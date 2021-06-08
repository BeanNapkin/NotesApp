package pro.fateeva.notes;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NoteDataMapping {

    public static class Fields {
        public static final String HEADER = "header";
        public static final String DESCRIPTION = "description";
        public static final String TEXT = "text";
        public static final String DATE = "date";
    }

    public static Note toNote(String id, Map<String, Object> doc) {
        String header = (String) doc.get(Fields.HEADER);
        String description = (String) doc.get(Fields.DESCRIPTION);
        String text = (String) doc.get(Fields.TEXT);
        Date date = (Date) doc.get(Fields.DATE);

        Note data = new Note(id, header, description, date, text);

        data.setId(id);

        return data;
    }

    public static Map<String, Object> toDocumnet(Note note){
        Map<String, Object> document = new HashMap<>();

        document.put(Fields.HEADER, note.getHeader());
        document.put(Fields.DESCRIPTION, note.getDescription());
        document.put(Fields.TEXT, note.getText());
        document.put(Fields.DATE, note.getDate());

        return document;
    }
}
