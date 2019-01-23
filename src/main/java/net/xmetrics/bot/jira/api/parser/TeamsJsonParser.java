package net.xmetrics.bot.jira.api.parser;

import com.atlassian.jira.rest.client.internal.json.JsonArrayParser;
import com.atlassian.jira.rest.client.internal.json.JsonParseUtil;
import lombok.AllArgsConstructor;
import net.xmetrics.bot.jira.dto.TeamDTO;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TeamsJsonParser implements JsonArrayParser<Iterable<TeamDTO>> {

  private final TeamJsonParser teamJsonParser;

  @Override
  public Iterable<TeamDTO> parse(JSONArray json) throws JSONException {
    return JsonParseUtil.parseJsonArray(json, teamJsonParser);
  }
}