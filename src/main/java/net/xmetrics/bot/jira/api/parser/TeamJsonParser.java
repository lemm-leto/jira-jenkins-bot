package net.xmetrics.bot.jira.api.parser;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import net.xmetrics.bot.jira.dto.TeamDTO;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class TeamJsonParser implements JsonObjectParser<TeamDTO> {

  @Override
  public TeamDTO parse(JSONObject json) throws JSONException {
    return new TeamDTO(
      json.getInt("id"),
      json.getString("name"),
      json.optString("lead")
    );
  }
}
