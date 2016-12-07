package com.anna.recept.service.impl;

import com.anna.recept.dto.DepartDto;
import com.anna.recept.dto.IngridientDto;
import com.anna.recept.dto.ProportionDto;
import com.anna.recept.dto.ReceptDto;
import com.anna.recept.dto.ReceptXmlDto;
import com.anna.recept.dto.ReferenceDto;
import com.anna.recept.exception.Errors;
import com.anna.recept.exception.ReceptApplicationException;
import com.anna.recept.facade.IReceptFullFacade;
import com.anna.recept.service.ICategoryService;
import com.anna.recept.service.IDepartService;
import com.anna.recept.service.IFileService;
import com.anna.recept.service.IIngridientService;
import com.anna.recept.service.IProportionService;
import com.anna.recept.service.IReceptService;
import com.anna.recept.service.IReferenceService;
import com.anna.recept.service.ITagService;
import com.anna.recept.service.IXmlService;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DomXmlService implements IXmlService {

    private static final String ROOT_ELEMENT = "Recept";
    private static final String NAME_ELEMENT = "Name";
    private static final String DESCRIPTION_ELEMENT = "Description";
    private static final String DEPART_ELEMENT = "Depart";
    private static final String TAGS_ELEMENT = "Tags";
    private static final String TAG_ELEMENT = "Tag";
    private static final String REFERENCES_ELEMENT = "References";
    private static final String REFERENCE_ELEMENT = "Reference";
    private static final String PROPORTIONS_ELEMENT = "Proportions";
    private static final String PROPORTION_ELEMENT = "Proportion";
    private static final String INGRIDIENT_ELEMENT = "Ingridient";
    private static final String NORMA_ELEMENT = "Norma";
    //xslt file has reference to this name
    private static final String IMAGE_NAME = "picture.png";

    @Autowired
    private IReceptService receptService;

    @Autowired
    private IReceptFullFacade receptFacade;

    @Autowired
    private IDepartService departService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ITagService tagService;

    @Autowired
    private IReferenceService refService;

    @Autowired
    private IIngridientService ingService;

    @Autowired
    private IProportionService propService;

    @Autowired
    private IFileService fileService;

    @Autowired
    ServletContext context;

    private static final Logger logger = Logger.getLogger(DomXmlService.class);

    @Override
    public byte[] getPdfFromRecept(Integer receptId) throws IOException {
        String xmlName = UUID.randomUUID().toString().concat(".xml");
        File xml = new File(xmlName);
        constructXml(getXmlDto(receptId), xml);
        File pdf = createPdf(xml, fileService.getReceptMainFoto(receptId));
        Path path = Paths.get(pdf.getAbsolutePath());
        byte[] result = Files.readAllBytes(path);
        pdf.delete();
        return result;
    }

    private ReceptXmlDto getXmlDto(Integer receptId) {
        ReceptXmlDto recept = new ReceptXmlDto();
        ReceptDto receptDto = receptFacade.showRecept(receptId);
        Optional.ofNullable(receptDto.getName()).ifPresent(name -> recept.setName(name));
        Optional.ofNullable(receptDto.getText()).ifPresent(text -> recept.setText(text));
        Optional.ofNullable(receptDto.getDepartId()).map(DepartDto::getName)
                .ifPresent(name -> recept.setDepartName(name));
        Optional.ofNullable(receptDto.getTags())
                .map(tags -> tags.stream().map(tag -> tag.getName()).collect(Collectors.toList()))
                .ifPresent(tags -> recept.setTags(tags));
        Optional.ofNullable(receptDto.getReferences())
                .map(refs -> refs.stream().map(ref -> ref.getReceptReferenceName()).collect(Collectors.toList()))
                .ifPresent(refs -> recept.setReferences(refs));

        Map<String, String> proportions = new HashMap<>();
        for (ProportionDto prop : receptDto.getProportions()) {
            proportions.put(prop.getIngridient().getName(), prop.getNorma());
        }
        if (!proportions.isEmpty()) {
            recept.setProportions(proportions);
        }

        return recept;
    }

    private void constructXml(ReceptXmlDto recept, File sourceXml) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.warn("Parser configuration error");
            throw new ReceptApplicationException(Errors.PDF_TRANSFORM_ERROR);
        }
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement(ROOT_ELEMENT);
        doc.appendChild(rootElement);

        Optional.ofNullable(recept.getName()).ifPresent(name -> {
            Element nameEl = doc.createElement(NAME_ELEMENT);
            nameEl.appendChild(doc.createTextNode(name));
            rootElement.appendChild(nameEl);
        });
        Optional.ofNullable(recept.getText()).ifPresent(text -> {
            Element descriptionEl = doc.createElement(DESCRIPTION_ELEMENT);
            descriptionEl.appendChild(doc.createTextNode(text));
            rootElement.appendChild(descriptionEl);
        });
        Optional.ofNullable(recept.getDepartName()).ifPresent(depart -> {
            Element departEl = doc.createElement(DEPART_ELEMENT);
            departEl.appendChild(doc.createTextNode(depart));
            rootElement.appendChild(departEl);
        });
        Optional.ofNullable(recept.getTags()).ifPresent(tags -> {
            Element tagsEl = doc.createElement(TAGS_ELEMENT);
            tags.stream().forEach((tag) -> {
                Element tagEl = doc.createElement(TAG_ELEMENT);
                tagEl.appendChild(doc.createTextNode(tag));
                tagsEl.appendChild(tagEl);
            });
            rootElement.appendChild(tagsEl);
        });
        Optional.ofNullable(recept.getReferences()).ifPresent(refs -> {
            Element referencesEl = doc.createElement(REFERENCES_ELEMENT);
            refs.stream().forEach((ref) -> {
                Element refEl = doc.createElement(REFERENCE_ELEMENT);
                refEl.appendChild(doc.createTextNode(ref));
                referencesEl.appendChild(refEl);
            });
            rootElement.appendChild(referencesEl);
        });
        Optional.ofNullable(recept.getProportions()).ifPresent(props -> {
            Element proportionsEl = doc.createElement(PROPORTIONS_ELEMENT);
            for (Map.Entry<String, String> entry : props.entrySet()) {
                Element proportionEl = doc.createElement(PROPORTION_ELEMENT);

                Element ingridientEl = doc.createElement(INGRIDIENT_ELEMENT);
                ingridientEl.appendChild(doc.createTextNode(entry.getKey()));

                Optional.ofNullable(entry.getValue()).ifPresent(norma -> {
                    Element normaEl = doc.createElement(NORMA_ELEMENT);
                    normaEl.appendChild(doc.createTextNode(entry.getValue()));
                    proportionEl.appendChild(normaEl);
                });
                proportionEl.appendChild(ingridientEl);
                proportionsEl.appendChild(proportionEl);
            }
            rootElement.appendChild(proportionsEl);
        });

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(sourceXml.getAbsolutePath());
            transformer.transform(source, result);

        } catch (TransformerException e) {
            logger.warn("Transformer error");
            throw new ReceptApplicationException(Errors.PDF_TRANSFORM_ERROR);
        }
    }

    private File createPdf(File sourceXml, byte[] picture) throws IOException {
        File langConfig = fileService.getLangConfig();

        DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();

        FopFactory fopFactory;
        try {
            Configuration cfg = cfgBuilder.buildFromFile(langConfig);
            fopFactory = FopFactory.newInstance();
            fopFactory.setUserConfig(cfg);
        } catch (SAXException | ConfigurationException e) {
            logger.warn("SAX or configuration exception");
            sourceXml.delete();
            throw new ReceptApplicationException(Errors.PDF_TRANSFORM_ERROR);
        }
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

        String pdfName = UUID.randomUUID().toString().concat(".pdf");
        File pdfFile = new File(pdfName);
        File xsltFile = fileService.getXslFile();

        OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));

        Fop fop;
        try {
            fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile.getAbsolutePath()));
            transformer.setParameter("versionParam", "1.0");
            Source src = new StreamSource(sourceXml);
            Result res = new SAXResult(fop.getDefaultHandler());
            transformToPdf(picture, src, res, transformer);
        } catch (FOPException | TransformerConfigurationException e) {
            logger.warn("FOP configuration exception");
            pdfFile.delete();
            throw new ReceptApplicationException(Errors.PDF_TRANSFORM_ERROR);
        } finally {
            out.close();
            sourceXml.delete();
        }
        return pdfFile;
    }

    private synchronized void  transformToPdf(byte[] picture, Source src, Result res, Transformer transformer) {
        File image = new File(IMAGE_NAME);
        try {
            if (picture != null) {
                FileUtils.writeByteArrayToFile(image, picture);
            }
            transformer.transform(src, res);
        } catch (IOException | TransformerException e) {
            logger.warn("Transform PDF exception");
            throw new ReceptApplicationException(Errors.PDF_TRANSFORM_ERROR);
        } finally {
            image.delete();
        }
    }

    @Override
    public Integer getReceptFromXml(MultipartFile file) throws IOException {
        File xml = new File(UUID.randomUUID().toString());
        file.transferTo(xml);
        checkXSD(xml);
        ReceptXmlDto dto = constructDto(getDocument(xml));
        return saveData(dto);
    }

    private Integer saveData(ReceptXmlDto dto) {
        ReceptDto recept = new ReceptDto();

        departService.findAllDepartments().stream()
                .filter((depart) -> depart.getName().equals(dto.getDepartName()))
                .findFirst().ifPresent((depart) -> {
            DepartDto departDto = new DepartDto();
            departDto.setId(depart.getId());
            recept.setDepartId(departDto);
        });

        if (recept.getDepartId() == null) {
            throw new ReceptApplicationException(Errors.DEPART_NOT_EXISTS);
        }

        recept.setName(dto.getName());
        recept.setText(dto.getText());

        Integer id = receptService.saveWithUniqueName(recept);

        dto.getTags().stream().forEach((tag) -> tagService.findTags().stream()
                .filter((availableTag) -> availableTag.getName().equalsIgnoreCase(tag))
                .findFirst()
                .ifPresent((filteredTag) -> categoryService.saveCategory(id, filteredTag.getId())));

        dto.getReferences().stream().forEach((ref) -> {
            if (receptService.getRecept(ref) != null) {
                ReferenceDto reference = new ReferenceDto();
                reference.setReceptReferenceId(receptService.getRecept(ref).getId());
                refService.saveReference(reference, id);
            }
        });

        for (Map.Entry<String, String> entry : dto.getProportions().entrySet()) {
            ingService.showAllIngridients().stream()
                    .filter((ing) -> ing.getName().equalsIgnoreCase(entry.getKey()))
                    .findFirst().ifPresent((ingridient) -> {
                ProportionDto proportion = new ProportionDto();
                proportion.setNorma(entry.getValue());
                IngridientDto ingridientDto = new IngridientDto();
                ingridientDto.setId(ingridient.getId());
                proportion.setIngridient(ingridientDto);
                propService.saveProportion(proportion, id);
            });
        }

        return id;
    }

    private void checkXSD(File xml) throws IOException {
        Source xmlFile = new StreamSource(xml);
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Schema schema;
        try {
            schema = schemaFactory.newSchema(fileService.getXsdScheme());
        } catch (SAXException e) {
            logger.warn("XSD parsing exception");
            throw new ReceptApplicationException(Errors.XSD_PARSING_ERROR);
        }
        Validator validator = schema.newValidator();
        try {
            validator.validate(xmlFile);
        } catch (SAXException e) {
            logger.warn("XML file not match XSD scheme");
            throw new ReceptApplicationException(Errors.XSD_VALIDATION_ERROR);
        }
    }

    private ReceptXmlDto constructDto(Document doc) {
        ReceptXmlDto recept = new ReceptXmlDto();

        recept.setName(doc.getElementsByTagName(NAME_ELEMENT).item(0).getFirstChild().getNodeValue());
        recept.setDepartName(doc.getElementsByTagName(DEPART_ELEMENT).item(0).getFirstChild().getNodeValue());
        recept.setText(doc.getElementsByTagName(DESCRIPTION_ELEMENT).item(0).getFirstChild().getNodeValue());

        List<String> tags = new ArrayList<>();
        for (int i = 0; i < doc.getElementsByTagName(TAG_ELEMENT).getLength(); i++) {
            Element tagEl = (Element) doc.getElementsByTagName(TAG_ELEMENT).item(i);
            tags.add(tagEl.getFirstChild().getNodeValue());
        }
        recept.setTags(tags);

        List<String> references = new ArrayList<>();
        for (int i = 0; i < doc.getElementsByTagName(REFERENCE_ELEMENT).getLength(); i++) {
            Element refEl = (Element) doc.getElementsByTagName(REFERENCE_ELEMENT).item(i);
            references.add(refEl.getFirstChild().getNodeValue());
        }
        recept.setReferences(references);

        Map<String, String> proportions = new HashMap<>();
        for (int i = 0; i < doc.getElementsByTagName(PROPORTION_ELEMENT).getLength(); i++) {
            Element proportionEl = (Element) doc.getElementsByTagName(PROPORTION_ELEMENT).item(i);
            String ing = proportionEl.getElementsByTagName(INGRIDIENT_ELEMENT).item(0).getFirstChild().getNodeValue();
            String norma = null;
            if (proportionEl.getElementsByTagName(NORMA_ELEMENT).item(0) != null &&
                    proportionEl.getElementsByTagName(NORMA_ELEMENT).item(0).getFirstChild() != null) {
                norma = proportionEl.getElementsByTagName(NORMA_ELEMENT).item(0).getFirstChild().getNodeValue();
            }
            proportions.put(ing, norma);
        }
        recept.setProportions(proportions);
        return recept;
    }

    private Document getDocument(File file) throws IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.warn("Parser configuration exception");
            file.delete();
            throw new ReceptApplicationException(Errors.XML_PARSING_ERROR);
        }

        dbf.setValidating(false);
        dbf.setNamespaceAware(true);
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);

        try {
            return db.parse(file);
        } catch (SAXException e) {
            throw new ReceptApplicationException(Errors.XML_PARSING_ERROR);
        } finally {
            file.delete();
        }
    }

}
