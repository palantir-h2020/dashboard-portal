package eu.ubitech.palantir.portal.config;

import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer;

public class AnalysisConfigurer implements ElasticsearchAnalysisConfigurer {

  @Override
  public void configure(ElasticsearchAnalysisConfigurationContext context) {
    context.analyzer("latin").custom().tokenizer("standard").tokenFilters("asciifolding", "lowercase");

    context.tokenFilter("autocomplete_filter").type("edge_ngram").param("min_gram", 3).param("max_gram", 20);

    context.analyzer("autocomplete").custom().tokenizer("standard").tokenFilters("asciifolding", "lowercase",
        "autocomplete_filter");

  }
}
