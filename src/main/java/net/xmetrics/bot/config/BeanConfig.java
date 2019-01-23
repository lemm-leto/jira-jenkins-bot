package net.xmetrics.bot.config;

import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousHttpClientFactory;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class BeanConfig {

  @Value("${jira.url}") private String jiraUrl;
  @Value("${jira.username}") private String username;
  @Value("${jira.password}") private char[] password;

  @Bean
  public DisposableHttpClient jiraHttpClient() throws URISyntaxException {
    URI jiraURI = new URI(jiraUrl);

    return new AsynchronousHttpClientFactory().createClient(
      jiraURI,
      new BasicHttpAuthenticationHandler(username, new String(password))
    );
  }



}
