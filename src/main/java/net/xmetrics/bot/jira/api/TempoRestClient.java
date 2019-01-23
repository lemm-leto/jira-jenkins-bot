package net.xmetrics.bot.jira.api;

import io.atlassian.util.concurrent.Promise;
import net.xmetrics.bot.jira.dto.MemberDTO;
import net.xmetrics.bot.jira.dto.TeamDTO;

public interface TempoRestClient {

  Promise<Iterable<TeamDTO>> findTeams();

  Promise<Iterable<MemberDTO>> findMembers(int teamId);
}
