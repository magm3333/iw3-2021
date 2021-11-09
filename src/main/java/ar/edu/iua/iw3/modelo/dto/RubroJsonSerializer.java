package ar.edu.iua.iw3.modelo.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ar.edu.iua.iw3.modelo.Rubro;

public class RubroJsonSerializer extends StdSerializer<Rubro> {
	private static final long serialVersionUID = -6689462676540336093L;

	public RubroJsonSerializer(Class<Rubro> t) {
		super(t);
	}

	@Override
	public void serialize(Rubro value, JsonGenerator gen, SerializerProvider provider) throws IOException {

		gen.writeStartObject();
		{
			gen.writeNumberField("id", value.getId());
			gen.writeStringField("rubro", value.getRubro());
		}
		gen.writeEndObject();

	}

}
