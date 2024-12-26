// package com.adobe.aem.demo.core.servlets;

// import static org.mockito.Mockito.*;
// import static org.junit.jupiter.api.Assertions.*;

// import org.apache.sling.api.SlingHttpServletRequest;
// import org.apache.sling.api.SlingHttpServletResponse;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import java.io.PrintWriter;
// import java.io.StringWriter;

// public class searchTest {

//     private search servlet;

//     @Mock
//     private SlingHttpServletRequest request;

//     @Mock
//     private SlingHttpServletResponse response;

//     private StringWriter responseWriter;

//     @BeforeEach
//     public void setUp() throws Exception {
//         MockitoAnnotations.initMocks(this);
//         servlet = new search();
//         responseWriter = new StringWriter();
//         when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
//     }

//     @Test
//     public void testDoGetWithSessionParam() throws Exception {
//         when(request.getParameter("session")).thenReturn("true");
//         when(request.getParameter("random")).thenReturn(null);

//         servlet.doGet(request, response);
// //
// //        verify(response).setContentType("application/json");
// //        verify(response).setCharacterEncoding("UTF-8");

//         String jsonResponse = responseWriter.toString();
//         assertTrue(jsonResponse.contains("randomWord"));
//     }

//     @Test
//     public void testDoGetWithRandomParam() throws Exception {
//         when(request.getParameter("session")).thenReturn(null);
//         when(request.getParameter("random")).thenReturn("true");

//         servlet.doGet(request, response);
// //
// //        verify(response).setContentType("application/json");
// //        verify(response).setCharacterEncoding("UTF-8");

//         String jsonResponse = responseWriter.toString();
//         assertTrue(jsonResponse.contains("randomWord"));
//     }

//     @Test
//     public void testDoGetWithNoValidParams() throws Exception {
//         when(request.getParameter("session")).thenReturn(null);
//         when(request.getParameter("random")).thenReturn(null);

//         servlet.doGet(request, response);
// //
// //        verify(response).setContentType("application/json");
// //        verify(response).setCharacterEncoding("UTF-8");

//         String jsonResponse = responseWriter.toString();
//         assertTrue(jsonResponse.contains("error"));
//         assertTrue(jsonResponse.contains("Invalid parameter. Use 'session' or 'random'."));
//     }
// }
