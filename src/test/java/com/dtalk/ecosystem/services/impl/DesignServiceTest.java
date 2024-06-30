package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.entities.Design;
import com.dtalk.ecosystem.entities.Field;
import com.dtalk.ecosystem.entities.Tag;
import com.dtalk.ecosystem.entities.User;
import com.dtalk.ecosystem.repositories.DesignRepository;
import com.dtalk.ecosystem.repositories.FieldRepository;
import com.dtalk.ecosystem.repositories.TagRepository;
import com.dtalk.ecosystem.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
public class DesignServiceTest {

    @Mock
    private DesignRepository designRepository;

    @Mock
    private UserRepository userRepository;


    @Mock
    private TagRepository tagRepository;

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FileStorageService fileStorageService;

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

        User user = new User();
        user.setIdUser(idDesigner);

        when(userRepository.findById(idDesigner)).thenReturn(Optional.of(user));
        when(fileStorageService.saveFile(imageFile)).thenReturn("imageFilePath");
        when(fileStorageService.saveFile(originFile)).thenReturn("originFilePath");

        Tag tag = new Tag();
        tag.setName("TestTag");
        when(tagRepository.findByName("TestTag")).thenReturn(Optional.of(tag));

        Field field = new Field();
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
        assertEquals(user, createdDesign.getUser());
        assertEquals(1, createdDesign.getTags().size());
        assertEquals(1, createdDesign.getFields().size());
        verify(designRepository, times(1)).save(createdDesign);
    }




    @Test
    public void testAcceptDesign() {
        Long designId = 1L;
        Design design = new Design();
        design.setIsAccepted(false);

        when(designRepository.findById(designId)).thenReturn(Optional.of(design));

        Boolean result = designService.acceptDesign(designId);

        assertTrue(result);
        assertTrue(design.getIsAccepted());
    }

    @Test
    public void testDisacceptDesign() {
        Long designId = 1L;
        Design design = new Design();
        design.setIsAccepted(true);

        when(designRepository.findById(designId)).thenReturn(Optional.of(design));

        Boolean result = designService.disacceptDesign(designId);

        assertTrue(result);
        assertFalse(design.getIsAccepted());
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
    User testUser = new User();
    testUser.setIdUser(1L);
    when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

    Design publishedAcceptedDesign = new Design();
    publishedAcceptedDesign.setIdDesign(1L);
    publishedAcceptedDesign.setName("Published and Accepted Design");
    publishedAcceptedDesign.setIsPublished(true);
    publishedAcceptedDesign.setIsAccepted(true);
    publishedAcceptedDesign.setUser(testUser);

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
    public void testFindDesignsByUserEquals() {


        // Initialize test data
        User testUser = new User();
        testUser.setIdUser(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Design publishedAcceptedDesign = new Design();
        publishedAcceptedDesign.setIdDesign(1L);
        publishedAcceptedDesign.setName("Published and Accepted Design");
        publishedAcceptedDesign.setUser(testUser);

        Design unpublishedDesign = new Design();
        unpublishedDesign.setIdDesign(2L);
        unpublishedDesign.setName("Unpublished Design");
        unpublishedDesign.setUser(testUser);

        Design rejectedDesign = new Design();
        rejectedDesign.setIdDesign(3L);
        rejectedDesign.setName("Rejected Design");
        rejectedDesign.setUser(testUser);

        // Mock the method findDesignsByUserEquals
        List<Design> expectedDesigns = Arrays.asList(publishedAcceptedDesign, unpublishedDesign, rejectedDesign);
        when(designRepository.findDesignsByUserEquals(testUser)).thenReturn(expectedDesigns);

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
