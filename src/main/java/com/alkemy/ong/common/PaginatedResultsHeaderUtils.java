package com.alkemy.ong.common;

import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component("paginatedResultsHeaderUtils")
public class PaginatedResultsHeaderUtils {

  private static final String PAGE_PARAM = "page";
  private static final String NEXT_REL = "next";
  private static final String PREV_REL = "prev";


  public void addLinkHeaderOnPagedResult(final UriComponentsBuilder uriBuilder,
      final HttpServletResponse response, final int page, final int totalPages,
      final String pagePath) {
    uriBuilder.path("/" + pagePath);

    final StringBuilder linkHeader = new StringBuilder();

    if (hasNextPage(page, totalPages)) {
      final String uriForNextPage =
          createLinkHeader(constructNextPageUri(uriBuilder, page), NEXT_REL);

      linkHeader.append(uriForNextPage);
    }

    if (hasPreviousPage(page)) {
      final String uriForPrevPage =
          createLinkHeader(constructPrevPageUri(uriBuilder, page), PREV_REL);

      linkHeader.append(uriForPrevPage);
    }

    response.addHeader("Link", linkHeader.toString());
  }

  private String constructNextPageUri(UriComponentsBuilder uriBuilder, final int page) {
    return uriBuilder.replaceQueryParam(PAGE_PARAM, page + 1).build().encode().toUriString();
  }

  private String constructPrevPageUri(UriComponentsBuilder uriBuilder, final int page) {
    return uriBuilder.replaceQueryParam(PAGE_PARAM, page - 1).build().encode().toUriString();
  }

  private boolean hasNextPage(final int page, final int totalPages) {
    return page < totalPages - 1;
  }

  private boolean hasPreviousPage(final int page) {
    return page > 0;
  }

  private static String createLinkHeader(final String uri, final String rel) {
    return "<" + uri + ">; rel=\"" + rel + "\"";
  }
}