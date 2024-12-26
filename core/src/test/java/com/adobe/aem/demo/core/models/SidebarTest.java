// package com.adobe.aem.demo.core.models;

// import static org.junit.jupiter.api.Assertions.*;

// import java.util.List;

// import org.apache.sling.api.resource.Resource;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;

// import io.wcm.testing.mock.aem.junit5.AemContext;
// import io.wcm.testing.mock.aem.junit5.AemContextExtension;

// @ExtendWith(AemContextExtension.class)
// class SidebarTest {

//     private final AemContext context = new AemContext();
//     private Sidebar sidebar;

//     @BeforeEach
//     void setUp() {
//         // Create parent resource with properties
//         context.create().resource("/content/sidebar",
//                 "logopath", "/content/dam/logo.png",
//                 "logomobileimage", "/content/dam/logo-mobile.png",
//                 "logolink", "https://example.com",
//                 "checkbox", "true",
//                 "country", "USA"
//         );

//         // Create child resources
//         context.create().resource("/content/sidebar/mul1/item1",
//                 "text1", "Sample Text",
//                 "image1", "/content/dam/image1.png"
//         );

//         context.create().resource("/content/sidebar/mul2/item1",
//                 "icon1", "icon-sample",
//                 "mobileicon1", "mobile-icon-sample"
//         );

//         context.create().resource("/content/sidebar/mul2/item1/nested/item1",
//                 "nestedText", "Nested Text",
//                 "nestedIcon", "Nested Icon"
//         );

//         // Adapt the main resource to the Sidebar model
//         Resource sidebarResource = context.resourceResolver().getResource("/content/sidebar");
//         assertNotNull(sidebarResource, "Sidebar resource should not be null");
//         sidebar = sidebarResource.adaptTo(Sidebar.class);
//         assertNotNull(sidebar, "Sidebar model adaptation should not be null");
//     }

//     @Test
//     void testSidebarFields() {
//         // Validate direct fields
//         assertEquals("/content/dam/logo.png", sidebar.getLogopath());
//         assertEquals("/content/dam/logo-mobile.png", sidebar.getLogomobileimage());
//         assertEquals("https://example.com", sidebar.getLogolink());
//         assertEquals("true", sidebar.getCheckbox());
//         assertEquals("USA", sidebar.getCountry());
//     }

//     @Test
//     void testGetMul1() {
//         // Validate child resources (Multi1)
//         List<Multi1> multi1List = sidebar.getMul1();//to iterate multi fields.
//         assertNotNull(multi1List, "Multi1 list should not be null");
//         assertEquals(1, multi1List.size(), "Multi1 list should contain one item");

//         Multi1 multi1 = multi1List.get(0);
//         assertEquals("Sample Text", multi1.getText1());
//         assertEquals("/content/dam/image1.png", multi1.getImage1());
//     }

//     @Test
//     void testGetMul2() {
//         // Validate child resources (Multi2)
//         List<Multi2> multi2List = sidebar.getMul2();
//         assertNotNull(multi2List, "Multi2 list should not be null");
//         assertEquals(1, multi2List.size(), "Multi2 list should contain one item");

//         Multi2 multi2 = multi2List.get(0);
//         assertEquals("icon-sample", multi2.getIcon1());
//         assertEquals("mobile-icon-sample", multi2.getMobileicon1());

//         // Validate nested resources in Multi2
//         List<Nest1> nestedList = multi2.getNested();
//         assertNotNull(nestedList, "Nested list should not be null");
//         assertEquals(1, nestedList.size(), "Nested list should contain one item");

//         Nest1 nested = nestedList.get(0);
//         assertEquals("Nested Text", nested.getNestedText());
//         assertEquals("Nested Icon", nested.getNestedIcon());
//     }
// }
