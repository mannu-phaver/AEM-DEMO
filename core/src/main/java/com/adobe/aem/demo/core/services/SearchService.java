package com.adobe.aem.demo.core.services;

import java.util.List;

public interface SearchService {
    List<String> searchByKeyword(String keyword);
}
