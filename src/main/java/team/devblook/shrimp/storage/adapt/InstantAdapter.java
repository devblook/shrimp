package team.devblook.shrimp.storage.adapt;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.Instant;

public class InstantAdapter implements JsonSerializer<Instant>, JsonDeserializer<Instant> {

  @Override
  public JsonElement serialize(
    final Instant instant,
    final Type type,
    final JsonSerializationContext jsonSerializationContext
  ) {
    return new JsonPrimitive(instant.toString());
  }

  @Override
  public Instant deserialize(
    final JsonElement jsonElement,
    final Type type,
    final JsonDeserializationContext jsonDeserializationContext
  ) throws JsonParseException {
    return Instant.parse(jsonElement.getAsString());
  }
}
