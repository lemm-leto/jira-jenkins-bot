package net.xmetrics.bot.jira.client;

import com.atlassian.jira.rest.client.api.JiraRestClient;

import net.xmetrics.bot.jira.api.TempoRestClient;

public interface JiraRestClientExtended extends JiraRestClient {
  TempoRestClient getTempoClient();
}
