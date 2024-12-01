package somepackage;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter class to convert a comma-separated String to a List and vice versa.
 * This converter is used to handle multi-value input fields in JSF forms.
 */
@FacesConverter("listConverter")
public class ListConverter implements Converter {

    /**
     * Converts a comma-separated String to a List of Strings.
     *
     * @param context FacesContext for the request being processed.
     * @param component UIComponent for the tag associated with this conversion.
     * @param value String value to be converted.
     * @return List of Strings or null if the input value is null or empty.
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    /**
     * Converts a List of Strings to a comma-separated String.
     *
     * @param context FacesContext for the request being processed.
     * @param component UIComponent for the tag associated with this conversion.
     * @param value List of Strings to be converted.
     * @return Comma-separated String or empty String if the input value is null.
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        List<?> list = (List<?>) value;
        return list.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}