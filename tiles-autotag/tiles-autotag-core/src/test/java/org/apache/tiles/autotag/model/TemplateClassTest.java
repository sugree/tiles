/**
 *
 */
package org.apache.tiles.autotag.model;

import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

/**
 * @author antonio
 *
 * @version $Rev$ $Date$
 */
public class TemplateClassTest {

    /**
     * Test method for {@link org.apache.tiles.autotag.model.TemplateClass#TemplateClass(java.lang.String)}.
     */
    @Test
    public void testTemplateConstructor1() {
        TemplateClass templateClass = new TemplateClass("name");
        assertEquals("name", templateClass.getName());
        assertNull(templateClass.getTagName());
        assertNull(templateClass.getTagClassPrefix());
        assertNull(templateClass.getExecuteMethod());
        Collection<TemplateParameter> params = templateClass.getParameters();
        assertTrue(params.isEmpty());
    }

    /**
     * Test method for {@link org.apache.tiles.autotag.model.TemplateClass#TemplateClass(java.lang.String, java.lang.String, java.lang.String, org.apache.tiles.autotag.model.TemplateMethod)}.
     */
    @Test
    public void testTemplateConstructor2() {
        TemplateMethod method = createMock(TemplateMethod.class);

        replay(method);
        TemplateClass templateClass = new TemplateClass("name", "tagName", "tagClassPrefix", method);
        assertEquals("name", templateClass.getName());
        assertEquals("tagName", templateClass.getTagName());
        assertEquals("tagClassPrefix", templateClass.getTagClassPrefix());
        assertEquals(method, templateClass.getExecuteMethod());
        verify(method);
    }

    /**
     * Test method for {@link org.apache.tiles.autotag.model.TemplateClass#setDocumentation(java.lang.String)}.
     */
    @Test
    public void testSetDocumentation() {
        TemplateClass templateClass = new TemplateClass("name");
        templateClass.setDocumentation("docs");
        assertEquals("docs", templateClass.getDocumentation());
    }

    /**
     * Test method for {@link org.apache.tiles.autotag.model.TemplateClass#getParameters()}.
     */
    @Test
    public void testGetParameters() {
        TemplateParameter param1 = createMock(TemplateParameter.class);
        TemplateParameter param2 = createMock(TemplateParameter.class);
        TemplateParameter param3 = createMock(TemplateParameter.class);
        TemplateParameter param4 = createMock(TemplateParameter.class);
        TemplateMethod method = createMock(TemplateMethod.class);
        List<TemplateParameter> params = new ArrayList<TemplateParameter>();

        expect(method.getParameters()).andReturn(params);
        expect(param1.isRequest()).andReturn(true);
        expect(param2.isRequest()).andReturn(false);
        expect(param2.isBody()).andReturn(true);
        expect(param3.isRequest()).andReturn(false);
        expect(param3.isBody()).andReturn(false);
        expect(param4.isRequest()).andReturn(false);
        expect(param4.isBody()).andReturn(false);
        expect(param3.getName()).andReturn("param1");
        expect(param4.getName()).andReturn("param2");

        replay(param1, param2, param3, param4, method);
        params.add(param1);
        params.add(param2);
        params.add(param3);
        params.add(param4);

        TemplateClass templateClass = new TemplateClass("name", "tagName", "tagClassPrefix", method);
        Collection<TemplateParameter> returnedParams = templateClass.getParameters();
        Iterator<TemplateParameter> paramIt = returnedParams.iterator();
        assertSame(param3, paramIt.next());
        assertSame(param4, paramIt.next());
        assertFalse(paramIt.hasNext());
        verify(param1, param2, param3, param4, method);
    }

    /**
     * Test method for {@link org.apache.tiles.autotag.model.TemplateClass#hasBody()}.
     */
    @Test
    public void testHasBody() {
        TemplateMethod method = createMock(TemplateMethod.class);
        expect(method.hasBody()).andReturn(true);

        replay(method);
        TemplateClass templateClass = new TemplateClass("name", "tagName", "tagClassPrefix", method);
        assertTrue(templateClass.hasBody());
        verify(method);
    }

    /**
     * Test method for {@link org.apache.tiles.autotag.model.TemplateClass#toString()}.
     */
    @Test
    public void testToString() {
        TemplateMethod method = new TemplateMethod("method", new ArrayList<TemplateParameter>());
        TemplateClass templateClass = new TemplateClass("name", "tagName", "tagClassPrefix", method);
        assertEquals("TemplateClass [name=name, tagName=tagName, tagClassPrefix=tagClassPrefix, " +
        		"documentation=null, executeMethod=TemplateMethod " +
        		"[name=method, documentation=null, parameters={}]]",
                templateClass.toString());
    }

}
