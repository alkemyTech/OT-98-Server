package com.alkemy.ong.common;

import com.alkemy.ong.exception.PageOutOfRangeException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component("paginatedResultsHeaderUtils")
public class PaginatedResultsHeaderUtils {

  public static final int PAGE_SIZE = 10;

  private static final String PAGE_PARAM = "page";
  private static final String NEXT_REL = "next";
  private static final String PREV_REL = "prev";

  private static String createLinkHeader(final String uri, final String rel) {
    return "<" + uri + ">; rel=\"" + rel + "\"";
  }

  public void addLinkHeaderOnPagedResult(final UriComponentsBuilder uriBuilder,
      final HttpServletResponse response, final int page, final int totalPages,
      final String pagePath) throws PageOutOfRangeException {

    if (page > totalPages) {
      throw new PageOutOfRangeException("Page " + page + " out of range");
    }

    uriBuilder.path("/" + pagePath);

    final StringBuilder linkHeader = new StringBuilder();

    if (hasNextPage(page, totalPages)) {
      final String uriForNextPage =
          createLinkHeader(constructNextPageUri(uriBuilder, page), NEXT_REL);

      appendCommaIfNecessary(linkHeader);
      linkHeader.append(uriForNextPage);
    }

    if (hasPreviousPage(page)) {
      final String uriForPrevPage =
          createLinkHeader(constructPrevPageUri(uriBuilder, page), PREV_REL);

      appendCommaIfNecessary(linkHeader);
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

  private void appendCommaIfNecessary(StringBuilder linkHeader) {
    if (linkHeader.length() > 0) {
      linkHeader.append(", ");
    }
  }
}
