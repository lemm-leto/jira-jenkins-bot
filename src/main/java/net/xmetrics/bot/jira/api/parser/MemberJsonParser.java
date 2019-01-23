package net.xmetrics.bot.jira.api.parser;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import net.xmetrics.bot.jira.dto.MemberDTO;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class MemberJsonParser implements JsonObjectParser<MemberDTO> {

  private static DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");

  @Override
  public MemberDTO parse(JSONObject json) throws JSONException {
    try {
      String dateFrom = json.getJSONObject("membership").getString("dateFrom");
      String dateTo = json.getJSONObject("membership").getString("dateTo");

      return new MemberDTO(
        json.getInt("id"),
        json.getJSONObject("member").getString("displayname"),
        json.getJSONObject("member").getJSONObject("avatar").getString("48x48"),
        IOUtils.toByteArray(new URL(json.getJSONObject("member").getJSONObject("avatar").getString("48x48"))),
        json.getJSONObject("membership").getJSONObject("role").getString("name"),
        StringUtils.isBlank(dateFrom) ? null : dateFormat.parse(dateFrom),
        StringUtils.isBlank(dateTo) ? null : dateFormat.parse(dateTo),
        json.getJSONObject("member").getBoolean("activeInJira")
      );
    } catch (ParseException | IOException e) {
      throw new JSONException(e);
    }
  }
}
