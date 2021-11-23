package com.alkemy.ong.common;

public class ListTestUtils {

  public String extractURIByRel(String linkHeader, String rel) {
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

  public String extractTypeOfRelation(final String linkRelation) {
    final int positionOfEquals = linkRelation.indexOf('=');
    return linkRelation.substring(positionOfEquals + 2, linkRelation.length() - 1).trim();
  }
}
