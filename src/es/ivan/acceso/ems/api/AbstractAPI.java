package es.ivan.acceso.ems.api;

import es.ivan.acceso.ems.Ems;
import es.ivan.acceso.ems.database.Database;

public abstract class AbstractAPI {

    protected final Database database = Ems.getInstance().getDatabase();
}
