package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.entities.Design;
import com.dtalk.ecosystem.entities.FieldDesigner;
import com.dtalk.ecosystem.entities.Tag;
import com.dtalk.ecosystem.entities.users.Designer;
import com.dtalk.ecosystem.repositories.*;
import com.dtalk.ecosystem.services.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class DesignServiceTest {

    @Mock
    private DesignRepository designRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DesignerRepository designerRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private DesignServiceImpl designService;



    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);


    }

    @Test
    public void testGetDesignById() {
        Design design = new Design();
        design.setIdDesign(1L);
        when(designRepository.findById(1L)).thenReturn(Optional.of(design));

        Design result = designService.getDesignById(1L);
       assertEquals(1L,result.getIdDesign());
    }

    @Test
    public void testRetrieveAllDesigns() {
        List<Design> designs = List.of(new Design(), new Design());
        when(designRepository.findAll()).thenReturn(designs);

        List<Design> result = designService.retrieveAllDesgins();
        assertEquals(designs.size(), result.size());
    }


    @Test
    public void testCreateDesign() throws IOException {
        // Arrange
        String name = "Test Design";
        double price = 100.0;
        String description = "This is a test design";
        MultipartFile imageFile = mock(MultipartFile.class);
        MultipartFile originFile = mock(MultipartFile.class);
        Long idDesigner = 1L;
        List<String> tagNames = new ArrayList<>();
        tagNames.add("TestTag");
        List<String> fieldTitles = new ArrayList<>();
        fieldTitles.add("TestField");

        Designer designer = new Designer();
        designer.setIdUser(idDesigner);

        when(designerRepository.findById(idDesigner)).thenReturn(Optional.of(designer));
        when(fileStorageService.saveFile(imageFile)).thenReturn("imageFilePath");
        when(fileStorageService.saveFile(originFile)).thenReturn("originFilePath");

        Tag tag = new Tag();
        tag.setName("TestTag");
        when(tagRepository.findByName("TestTag")).thenReturn(Optional.of(tag));

        FieldDesigner field = new FieldDesigner();
        field.setTitle("TestField");
        when(fieldRepository.findByTitle("TestField")).thenReturn(Optional.of(field));

        when(designRepository.save(any(Design.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Design createdDesign = designService.createDesign(name, price, description, imageFile, originFile, idDesigner, tagNames, fieldTitles);

        // Assert
        assertEquals(name, createdDesign.getName());
        assertEquals(price, createdDesign.getPrice());
        assertEquals(description, createdDesign.getDescription());
        assertEquals("imageFilePath", createdDesign.getImagePath());
        assertEquals("originFilePath", createdDesign.getOriginFilePath());
        assertEquals(designer, createdDesign.getDesigner());
        assertEquals(1, createdDesign.getTags().size());
        assertEquals(1, createdDesign.getFields().size());
        verify(designRepository, times(1)).save(createdDesign);
    }




    @Test
    public void testAcceptDesign() {
        Designer d = new Designer();
        d.setEmail("fff@hhh.com");

        Long designId = 1L;
        Design design = new Design();
        design.setIsAccepted(false);
        design.setDesigner(d);
        when(designRepository.findById(designId)).thenReturn(Optional.of(design));

        Boolean result = designService.acceptDesign(designId);

        assertTrue(result);
        assertTrue(design.getIsAccepted());
        verify(emailService, times(1)).notification("fff@hhh.com", true, "NotificationDesignMail", "Notification Design");

    }

    @Test
    public void testDisacceptDesign() {

        Designer d = new Designer();
        d.setEmail("fff@hhh.com");

        Long designId = 1L;
        Design design = new Design();
        design.setIsAccepted(true);
        design.setDesigner(d);

        when(designRepository.findById(designId)).thenReturn(Optional.of(design));

        Boolean result = designService.disacceptDesign(designId);

        assertTrue(result);
        assertFalse(design.getIsAccepted());
        verify(emailService, times(1)).notification("fff@hhh.com", false, "NotificationDesignMail", "Notification Design");

    }
@Test
public void testPublishedDesign(){
    Long designId = 1L;
    Design design = new Design();
    design.setIdDesign(designId);
    design.setIsPublished(false);  // Assure que le design n'est pas publié au début

    when(designRepository.findById(designId)).thenReturn(Optional.of(design));

    Boolean result = designService.publishDesign(designId);

    assertTrue(result);
    assertTrue(design.getIsPublished());
    verify(designRepository).save(design);

}
@Test
    public void testUnpublishedDesign(){
        Long designId = 1L;
    Design design = new Design();
    design.setIdDesign(designId);
    design.setIsPublished(true);  // Assure que le design est publié au début

    when(designRepository.findById(designId)).thenReturn(Optional.of(design));

    Boolean result = designService.unpublishDesign(designId);

    assertTrue(result);
    assertFalse(design.getIsPublished());
    verify(designRepository).save(design);

}


@Test
public void testGetAllDesignsIsacceptedIsTrueAndIsPublishedIsTrue(){

    // Initialize test data
    Designer testUser = new Designer();
    testUser.setIdUser(1L);
    when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

    Design publishedAcceptedDesign = new Design();
    publishedAcceptedDesign.setIdDesign(1L);
    publishedAcceptedDesign.setName("Published and Accepted Design");
    publishedAcceptedDesign.setIsPublished(true);
    publishedAcceptedDesign.setIsAccepted(true);
    publishedAcceptedDesign.setDesigner(testUser);

    // Simulate the repository method
    when(designRepository.findDesignsByIsPublishedIsTrueAndIsAcceptedIsTrue())
            .thenReturn(Collections.singletonList(publishedAcceptedDesign));

    // Call the method to test
    List<Design> designs = designRepository.findDesignsByIsPublishedIsTrueAndIsAcceptedIsTrue();

    // Verify the results
    assertEquals(1, designs.size());
    assertEquals("Published and Accepted Design", designs.get(0).getName());

}

    @Test
    public void testFindDesignsByDesignerEquals() {


        // Initialize test data
        Designer testUser = new Designer();
        testUser.setIdUser(1L);
        when(designerRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Design publishedAcceptedDesign = new Design();
        publishedAcceptedDesign.setIdDesign(1L);
        publishedAcceptedDesign.setName("Published and Accepted Design");
        publishedAcceptedDesign.setDesigner(testUser);

        Design unpublishedDesign = new Design();
        unpublishedDesign.setIdDesign(2L);
        unpublishedDesign.setName("Unpublished Design");
        unpublishedDesign.setDesigner(testUser);

        Design rejectedDesign = new Design();
        rejectedDesign.setIdDesign(3L);
        rejectedDesign.setName("Rejected Design");
        rejectedDesign.setDesigner(testUser);

        // Mock the method findDesignsByUserEquals
        List<Design> expectedDesigns = Arrays.asList(publishedAcceptedDesign, unpublishedDesign, rejectedDesign);
        when(designRepository.findDesignsByDesignerEquals(testUser)).thenReturn(expectedDesigns);

        // Call the method under test
        List<Design> designs = designService.retrieveAllDesginByUser(testUser.getIdUser());

        // Assertions
        assertNotNull(designs);  // Ensure the returned list is not null
        assertEquals(3, designs.size());
        assertEquals("Published and Accepted Design", designs.get(0).getName());
        assertEquals("Unpublished Design", designs.get(1).getName());
        assertEquals("Rejected Design", designs.get(2).getName());
    }



}
