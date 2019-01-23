package net.xmetrics.bot.jira.api;

import com.atlassian.jira.rest.client.internal.async.AbstractAsynchronousRestClient;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import io.atlassian.util.concurrent.Promise;
import net.xmetrics.bot.jira.api.parser.MembersJsonParser;
import net.xmetrics.bot.jira.api.parser.TeamsJsonParser;
import net.xmetrics.bot.jira.dto.MemberDTO;
import net.xmetrics.bot.jira.dto.TeamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class AsynchronousTempoRestClient extends AbstractAsynchronousRestClient implements TempoRestClient {

  private static final String TEAMS_URI = "rest/tempo-teams/1/team";
  private static final String TEAM_MEMBERS_URI = "rest/tempo-teams/2/team/%d/member";

  private final MembersJsonParser membersJsonParser;
  private final TeamsJsonParser teamsJsonParser;
  private final URI baseUri;

  public AsynchronousTempoRestClient(
    @Autowired DisposableHttpClient disposableHttpClient,
    @Autowired MembersJsonParser membersJsonParser,
    @Autowired TeamsJsonParser teamsJsonParser,
    @Value("${jira.url}") String jiraUrl
  ) throws URISyntaxException {
    super(disposableHttpClient);

    this.membersJsonParser = membersJsonParser;
    this.teamsJsonParser = teamsJsonParser;
    this.baseUri = new URI(jiraUrl);
  }

  @Override
  public Promise<Iterable<TeamDTO>> findTeams() {
    UriBuilder uriBuilder = UriBuilder.fromUri(baseUri).path(TEAMS_URI);

    return getAndParse(uriBuilder.build(), teamsJsonParser);
  }

  @Override
  public Promise<Iterable<MemberDTO>> findMembers(int teamId) {
    UriBuilder uriBuilder = UriBuilder.fromUri(baseUri).path(String.format(TEAM_MEMBERS_URI, teamId));

    return getAndParse(uriBuilder.build(), membersJsonParser);
  }
}
