package com.anna.recept.repository;

import com.anna.recept.persist.Category;
import com.anna.recept.persist.Dapart;
import com.anna.recept.persist.Detail;
import com.anna.recept.persist.Ingridient;
import com.anna.recept.persist.Proportion;
import com.anna.recept.persist.Recept;
import com.anna.recept.persist.Reference;
import com.anna.recept.persist.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:testRepositoryContext.xml"})
public abstract class RepositoryTest {

    @Autowired
    protected ICategoryRepository categoryRepository;
    @Autowired
    protected ITagRepository tagRepository;
    @Autowired
    protected IReceptRepository receptRepository;
    @Autowired
    protected IDepartRepository departRepository;
    @Autowired
    protected IIngridientRepository ingridientRepository;
    @Autowired
    protected IProportionRepository proportionRepository;
    @Autowired
    protected IDetailRepository detailRepository;
    @Autowired
    protected IReferenceRepository referenceRepository;
    @Autowired
    protected ITestingRepository testingRepository;

    public static String INGRIDIENT_NAME = "ingridient";
    public static String RECEPT_NAME = "recept";
    public static String DEPART_NAME = "depart";
    public static String TAG_NAME = "tag";

    @Before
    public void setUp() throws Exception {
        emptyDB();
    }

    @After
    public void tearDown() throws Exception {
        emptyDB();
    }

    public void emptyDB() {
        testingRepository.deleteAll();

        proportionRepository.deleteAll();
        categoryRepository.deleteAll();
        detailRepository.deleteAll();
        referenceRepository.deleteAll();

        tagRepository.deleteAll();
        ingridientRepository.deleteAll();
        receptRepository.deleteAll();

        departRepository.deleteAll();
    }

    public Category constructCategory(int receptId, int tagId) {
        Category category = new Category();
        category.setReceptId(receptId);
        category.setTagId(tagId);
        return category;
    }

    public Tag constructTag() {
        Tag tag = new Tag();
        tag.setName(TAG_NAME);
        return tag;
    }

    public Reference constructReference(int receptId, int receptReferenceId) {
        Reference reference = new Reference();
        reference.setReceptId(receptId);
        reference.setReceptReferenceId(receptReferenceId);
        return reference;
    }

    public Detail constructDetail(int receptId) {
        Detail detail = new Detail();
        detail.setReceptId(receptId);
        return detail;
    }

    public Proportion constructProportion(int receptId, int ingridientId) {
        Proportion proportion = new Proportion();
        proportion.setNorma("norma");
        proportion.setReceptId(receptId);
        proportion.setIngridientId(ingridientId);
        return proportion;
    }

    public Ingridient constructIngridient() {
        Ingridient ingridient = new Ingridient();
        ingridient.setName(INGRIDIENT_NAME);
        return ingridient;
    }

    public Recept constructRecept(int departId) {
        Recept recept = new Recept();
        recept.setDepartId(departId);
        recept.setName(RECEPT_NAME);
        return recept;
    }

    public Dapart constructDepart() {
        Dapart depart = new Dapart();
        depart.setName(DEPART_NAME);
        return depart;
    }
}
