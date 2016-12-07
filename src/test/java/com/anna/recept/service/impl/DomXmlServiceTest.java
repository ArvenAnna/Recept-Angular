package com.anna.recept.service.impl;

import com.anna.recept.dto.DepartDto;
import com.anna.recept.dto.ReceptDto;
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
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DomXmlServiceTest {

    private static final String RECEPT_SCHEME = "xml/recept_scheme.xsd";
    private static final Integer RECEPT_ID = 23;

    @Mock
    private IFileService fileService;
    @Mock
    private IReceptService receptService;
    @Mock
    private IReceptFullFacade receptFacade;
    @Mock
    private IDepartService departService;
    @Mock
    private ICategoryService categoryService;
    @Mock
    private ITagService tagService;
    @Mock
    private IReferenceService refService;
    @Mock
    private IIngridientService ingService;
    @Mock
    private IProportionService propService;

    @InjectMocks
    private IXmlService sut = new DomXmlService();

    private Integer receptId;
    private String receptName = "Куриные гнёзда";
    private String receptDesc = "Приготовить";
    private String receptDepart = "Вторые блюда";
    private String receptIng = "Яйца";
    private String receptNorma = "7-8 шт";
    private String tag = "Вкусно";
    private String referenceName = "Каша";

    @Before
    public void configureMocks() throws IOException {
        when(fileService.getXsdScheme()).thenReturn(new File(RECEPT_SCHEME));
        when(receptService.saveWithUniqueName(any())).thenReturn(RECEPT_ID);
        when(departService.findAllDepartments()).thenReturn(constructDepart());
        when(receptFacade.showRecept(RECEPT_ID)).thenReturn(constructReceptDto());
    }

    @Test
    public void shouldGetReceptFromXml() throws IOException {
        MultipartFile mpFile = getMultipartMockFile();

        receptId = sut.getReceptFromXml(mpFile);

        assertThat(receptId, is(RECEPT_ID));
    }

    @Test
    public void shouldGetPdfFromRecept() throws IOException {
        byte[] pdf = sut.getPdfFromRecept(RECEPT_ID);

        assertNotNull(pdf);
    }

    private MultipartFile getMultipartMockFile() throws IOException {
        File file = new File("test.xml");
        String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Recept><Name>" + receptName + "</Name><Description>" +
                receptDesc + "</Description><Depart>" + receptDepart + "</Depart><Proportions><Proportion><Ingridient>" +
                receptIng + "</Ingridient><Norma>" + receptNorma + "</Norma></Proportion></Proportions>" +
                "<Tags><Tag>" + tag + "</Tag></Tags><References><Reference>" + referenceName + "</Reference></References></Recept>";
        FileUtils.writeStringToFile(file, data);
        Path path = Paths.get(file.getAbsolutePath());
        byte[] mpBytes = Files.readAllBytes(path);
        MultipartFile mpFile = new MockMultipartFile("test.xml", mpBytes);
        file.delete();
        return mpFile;
    }

    private List<DepartDto> constructDepart() {
        DepartDto depart = new DepartDto();
        depart.setName(receptDepart);
        return Arrays.asList(depart);
    }

    private ReceptDto constructReceptDto() {
        ReceptDto recept = new ReceptDto();
        recept.setName("name");
        recept.setText("text");
        DepartDto depart = new DepartDto();
        depart.setName("depart");
        return recept;
    }
}