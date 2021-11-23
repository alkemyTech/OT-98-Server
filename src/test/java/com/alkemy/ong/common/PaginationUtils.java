package com.alkemy.ong.common;

import com.alkemy.ong.model.response.ListMemberResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class PaginationUtils {

  private PaginationUtils() {

  }

  public static String extractURIByRel(String linkHeader, String rel) {
    String uriWithSpecifiedRel = null;
    String[] links = linkHeader.split(", ");
    String linkRelation;
    for (String link : links) {
      int positionOfSeparator = link.indexOf(';');
      linkRelation = link.substring(positionOfSeparator + 1, link.length()).trim();
      if (extractTypeOfRelation(linkRelation).equals(rel)) {
        uriWithSpecifiedRel = link.substring(1, positionOfSeparator - 1);
        break;
      }
    }

    return uriWithSpecifiedRel;
  }

  public static String extractTypeOfRelation(final String linkRelation) {
    final int positionOfEquals = linkRelation.indexOf('=');
    return linkRelation.substring(positionOfEquals + 2, linkRelation.length() - 1).trim();
  }

  public static String getLink(HttpHeaders headers) {
    return headers.getFirst("Link");
  }

}
