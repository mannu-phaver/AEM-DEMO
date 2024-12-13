// package com.adobe.aem.demo.core.models;

// import io.wcm.testing.mock.aem.junit5.AemContext;
// import io.wcm.testing.mock.aem.junit5.AemContextExtension;
// import org.apache.sling.api.resource.Resource;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;

// import java.io.IOException;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// @ExtendWith(AemContextExtension.class)
// class ExampleTest {

//     private final AemContext context = new AemContext();
//     private Example example;

//     @BeforeEach
//     void setUp() {
//         // Create a mock resource with the expected properties
//         context.create().resource("/content/example",
//                 "sling:resourceType", "/apps/demo1/components/example",
//                 "fname", "John",
//                 "student", "Doe",
//                 "Dropdown", "Option1",
//                 "path", "/content/sample"
//         );


//         // Adapt the resource to the Example model
//         Resource resource = context.resourceResolver().getResource("/content/example");
//         example = resource.adaptTo(Example.class);
//     }

//     @Test
//     void getFname() throws IOException {
//         assertEquals("John", example.getFname(), "The fname property should return 'John'");
//     }

//     @Test
//     void getStudent() throws IOException {
//         assertEquals("Doe", example.getStudent(), "The student property should return 'Doe'");
//     }

//     @Test
//     void getDropdown() throws IOException{
//         assertEquals("Option1", example.getDropdown(), "The Dropdown property should return 'Option1'");
//     }

//     @Test
//     void getPath() throws IOException{
//         assertEquals("/content/sample", example.getPath(), "The path property should return '/content/sample'");
//     }
// }
