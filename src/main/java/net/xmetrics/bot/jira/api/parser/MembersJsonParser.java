package net.xmetrics.bot.jira.api.parser;

import com.atlassian.jira.rest.client.internal.json.JsonArrayParser;
import com.atlassian.jira.rest.client.internal.json.JsonParseUtil;
import lombok.AllArgsConstructor;
import net.xmetrics.bot.jira.dto.MemberDTO;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MembersJsonParser implements JsonArrayParser<Iterable<MemberDTO>> {

  private final MemberJsonParser memberJsonParser;

  @Override
  public Iterable<MemberDTO> parse(JSONArray json) throws JSONException {
    return JsonParseUtil.parseJsonArray(json, memberJsonParser);
  }

}
