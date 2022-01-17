package eu.palantir.portal.dto.options;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class ToOptions {

    @DefaultValue("0")
    @QueryParam("index")
    public int index;

    @DefaultValue("10")
    @QueryParam("size")
    public int size;

    @QueryParam("sort")
    public ToOptionsSort sort;

}
