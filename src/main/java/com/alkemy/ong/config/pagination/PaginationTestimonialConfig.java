package com.alkemy.ong.config.pagination;

import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class PaginationTestimonialConfig implements
    ApplicationListener<PaginatedResultsRetrievedEvent> {

  @Override
  public void onApplicationEvent(PaginatedResultsRetrievedEvent event) {
    addLinkHeaderOnPagedResourceRetrieval(event.getUriBuilder(), event.getResponse(), event.getClazz(),
        event.getPage(), event.getTotalPages(), event.getPageSize());
  }

  public void addLinkHeaderOnPagedResourceRetrieval(final UriComponentsBuilder uriBuilder,
      final HttpServletResponse response, final Class clazz, final int page, final int totalPages,
      final int pageSize) {

    final String resourceName = clazz.getSimpleName().toString().toLowerCase();

    uriBuilder.path("/testimonial");

    final StringBuilder linkHeader = new StringBuilder();

    if (hasNextPage(page, totalPages)) {
      final String uriForNextPage = constructNextPageUri(uriBuilder, page, pageSize);
      linkHeader.append(createLinkHeader(uriForNextPage, "next"));
    }

    if (hasPreviousPage(page)) {
      final String uriForPrevPage = constructPrevPageUri(uriBuilder, page, pageSize);
      appendCommaIfNecessary(linkHeader);
      linkHeader.append(createLinkHeader(uriForPrevPage, "prev"));
    }

    response.addHeader("Link", linkHeader.toString());
  }


  public String constructNextPageUri(final UriComponentsBuilder uriBuilder, final int page,
      final int size) {
    return uriBuilder.replaceQueryParam("page", page + 1).replaceQueryParam("size", size).build()
        .encode().toUriString();
  }

  public String constructPrevPageUri(final UriComponentsBuilder uriBuilder, final int page,
      final int size) {
    return uriBuilder.replaceQueryParam("page", page - 1).replaceQueryParam("size", size).build()
        .encode().toUriString();
  }

  public boolean hasNextPage(final int page, final int totalPages) {
    return page < totalPages - 1;
  }

  public boolean hasPreviousPage(final int page) {
    return page > 0;
  }


  public void appendCommaIfNecessary(final StringBuilder linkHeader) {
    if (linkHeader.length() > 0) {
      linkHeader.append(", ");
    }
  }

  public static String createLinkHeader(final String uri, final String rel) {
    return "<" + uri + ">; rel=\"" + rel + "\"";
  }
}
