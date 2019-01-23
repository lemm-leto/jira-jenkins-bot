package net.xmetrics.bot.jira.client;

import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClient;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import net.xmetrics.bot.jira.api.TempoRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class AsynchronousJiraRestClientExtended extends AsynchronousJiraRestClient implements JiraRestClientExtended {

  private final TempoRestClient tempoRestClient;

  public AsynchronousJiraRestClientExtended(
    @Autowired TempoRestClient tempoRestClient,
    @Autowired DisposableHttpClient httpClient,
    @Value("${jira.url}") String jiraUrl
    ) throws URISyntaxException {
    super(
      new URI(jiraUrl),
      httpClient
    );
    this.tempoRestClient = tempoRestClient;
  }

  @Override
  public TempoRestClient getTempoClient() {
    return tempoRestClient;
  }
}
