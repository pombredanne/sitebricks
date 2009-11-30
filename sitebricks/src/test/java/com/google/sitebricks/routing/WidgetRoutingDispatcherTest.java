package com.google.sitebricks.routing;

import com.google.inject.Provider;
import com.google.sitebricks.Renderable;
import com.google.sitebricks.Respond;
import com.google.sitebricks.binding.FlashCache;
import com.google.sitebricks.binding.RequestBinder;
import com.google.sitebricks.rendering.resource.ResourcesService;
import static org.easymock.EasyMock.*;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeMethod;

/**
 * @author Dhanji R. Prasanna (dhanji@gmail.com)
 */
public class WidgetRoutingDispatcherTest {
    private static final String REDIRECTED_POST = "/redirect_post";
    private static final String REDIRECTED_GET = "/redirect_get";
    private static final String A_STATIC_RESOURCE_URI = "/not_thing";

    private Provider<FlashCache> flashCacheProvider;
    private FlashCache flashCache;

    @BeforeMethod
    @SuppressWarnings("unchecked")
    public final void initFlashCacheProvider(){
        flashCacheProvider = createMock(Provider.class);
        flashCache = createMock(FlashCache.class);

        expect(flashCacheProvider.get())
                .andReturn(flashCache).anyTimes();

        replay(flashCacheProvider);
    }

    @Test
    public final void dispatchRequestAndRespondOnGet() {
        final HttpServletRequest request = createMock(HttpServletRequest.class);
        PageBook pageBook = createMock(PageBook.class);
        PageBook.Page page = createMock(PageBook.Page.class);
        Renderable widget = createMock(Renderable.class);
        final Respond respond = createMock(Respond.class);
        RequestBinder binder = createMock(RequestBinder.class);

        Object pageOb = new Object() ;


        expect(request.getRequestURI())
                .andReturn("/thing")
                .anyTimes();

        expect(request.getContextPath())
                .andReturn("")
                .anyTimes();

        expect(request.getParameterMap())
                .andReturn(new HashMap());

        expect(pageBook.get("/thing"))
                .andReturn(page);

        binder.bind(request, pageOb);
        expectLastCall().once();

        expect(page.widget())
                .andReturn(widget);

        expect(page.instantiate())
                .andReturn(pageOb);

        expect(request.getMethod())
                .andReturn("GET");

        expect(page.doMethod("get", pageOb, "/thing", new HashMap<String, String[]>()))
                .andReturn(null);
//        expectLastCall().once();


        widget.render(pageOb, respond);
        expectLastCall().once();


        replay(request, page, pageBook, widget, respond, binder);

        Respond out = new WidgetRoutingDispatcher(pageBook, binder, new Provider<Respond>() {
            public Respond get() {
                return respond;
            }
        }, createNiceMock(ResourcesService.class), flashCacheProvider
        ).dispatch(request);


        assert out == respond : "Did not respond correctly";
        
        verify(request, page, pageBook, widget, respond, binder);

    }
    @Test
    public final void dispatchRequestToCorrectEventHandlerOnGet() {
        final HttpServletRequest request = createMock(HttpServletRequest.class);
        PageBook pageBook = createMock(PageBook.class);
        PageBook.Page page = createMock(PageBook.Page.class);
        Renderable widget = createMock(Renderable.class);
        final Respond respond = createMock(Respond.class);
        RequestBinder binder = createMock(RequestBinder.class);

        Object pageOb = new Object() ;


        expect(request.getRequestURI())
                .andReturn("/thing")
                .anyTimes();

        expect(request.getContextPath())
                .andReturn("")
                .anyTimes();

        expect(pageBook.get("/thing"))
                .andReturn(page);

        binder.bind(request, pageOb);
        expectLastCall().once();

        expect(page.widget())
                .andReturn(widget);

        expect(page.instantiate())
                .andReturn(pageOb);

        expect(request.getMethod())
                .andReturn("GET");

        final HashMap<String, String[]> parameterMap = new HashMap<String, String[]>();
        expect(request.getParameterMap())
                .andReturn(parameterMap);

        expect(page.doMethod("get", pageOb, "/thing", parameterMap))
                .andReturn(null);
//        expectLastCall().once();


        widget.render(pageOb, respond);
        expectLastCall().once();


        replay(request, page, pageBook, widget, respond, binder);

        Respond out = new WidgetRoutingDispatcher(pageBook, binder, new Provider<Respond>() {
            public Respond get() {
                return respond;
            }
        }, createNiceMock(ResourcesService.class), flashCacheProvider
        ).dispatch(request);


        assert out == respond : "Did not respond correctly";

        verify(request, page, pageBook, widget, respond, binder);

    }


    @Test
    public final void dispatchRequestAndRespondOnPost() {
        final HttpServletRequest request = createMock(HttpServletRequest.class);
        PageBook pageBook = createMock(PageBook.class);
        PageBook.Page page = createMock(PageBook.Page.class);
        Renderable widget = createMock(Renderable.class);
        final Respond respond = createMock(Respond.class);
        RequestBinder binder = createMock(RequestBinder.class);

        Object pageOb = new Object() ;

        expect(request.getRequestURI())
                .andReturn("/thing")
                .anyTimes();

        expect(request.getContextPath())
                .andReturn("")
                .anyTimes();

        expect(request.getParameterMap())
                .andReturn(new HashMap());

        expect(pageBook.get("/thing"))
                .andReturn(page);

        binder.bind(request, pageOb);
        expectLastCall().once();

        expect(page.widget())
                .andReturn(widget);

        expect(page.instantiate())
                .andReturn(pageOb);

        expect(request.getMethod())
                .andReturn("POST");

        //noinspection unchecked
        expect(page.doMethod(matches("post"), eq(pageOb), eq("/thing"), isA(Map.class)))
                .andReturn(null);
//        expectLastCall().once();


        widget.render(pageOb, respond);
        expectLastCall().once();


        replay(request, page, pageBook, widget, respond, binder);

        Respond out = new WidgetRoutingDispatcher(pageBook, binder, new Provider<Respond>() {
            public Respond get() {
                return respond;
            }
        }, createNiceMock(ResourcesService.class), flashCacheProvider
        ).dispatch(request);


        assert out == respond : "Did not respond correctly";

        verify(request, page, pageBook, widget, respond, binder);

    }

    @Test
    public final void dispatchRequestAndRedirectOnPost() {
        final HttpServletRequest request = createMock(HttpServletRequest.class);
        PageBook pageBook = createMock(PageBook.class);
        PageBook.Page page = createMock(PageBook.Page.class);
        Renderable widget = createMock(Renderable.class);
        final Respond respond = createMock(Respond.class);
        RequestBinder binder = createMock(RequestBinder.class);

        Object pageOb = new Object() ;

        expect(request.getRequestURI())
                .andReturn("/thing")
                .anyTimes();

        expect(request.getContextPath())
                .andReturn("")
                .anyTimes();

        expect(request.getParameterMap())
                .andReturn(new HashMap());

        expect(pageBook.get("/thing"))
                .andReturn(page);

        binder.bind(request, pageOb);
        expectLastCall().once();

//        expect(page.widget())
//                .andReturn(widget);

        expect(page.instantiate())
                .andReturn(pageOb);

        expect(request.getMethod())
                .andReturn("POST");

        respond.redirect(REDIRECTED_POST);

        //noinspection unchecked
        expect(page.doMethod(matches("post"), eq(pageOb), eq("/thing"), isA(Map.class)))
                .andReturn(REDIRECTED_POST);


//        widget.render(pageOb, respond);
//        expectLastCall().once();


        replay(request, page, pageBook, widget, respond, binder);

        Respond out = new WidgetRoutingDispatcher(pageBook, binder, new Provider<Respond>() {
            public Respond get() {
                return respond;
            }
        }, createNiceMock(ResourcesService.class), flashCacheProvider
        ).dispatch(request);


        assert out == respond : "Did not respond correctly";

        verify(request, page, pageBook, widget, respond, binder);

    }

    @Test
    public final void dispatchRequestAndRedirectOnGet() {
        final HttpServletRequest request = createMock(HttpServletRequest.class);
        PageBook pageBook = createMock(PageBook.class);
        PageBook.Page page = createMock(PageBook.Page.class);
        Renderable widget = createMock(Renderable.class);
        final Respond respond = createMock(Respond.class);
        RequestBinder binder = createMock(RequestBinder.class);

        Object pageOb = new Object() ;


        expect(request.getRequestURI())
                .andReturn("/thing")
                .anyTimes();

        expect(request.getContextPath())
                .andReturn("")
                .anyTimes();

        expect(request.getParameterMap())
                .andReturn(new HashMap());

        expect(pageBook.get("/thing"))
                .andReturn(page);

        binder.bind(request, pageOb);
        expectLastCall().once();

//        expect(page.widget())
//                .andReturn(widget);

        expect(page.instantiate())
                .andReturn(pageOb);

        expect(request.getMethod())
                .andReturn("GET");

        respond.redirect(REDIRECTED_GET);

        //noinspection unchecked
        expect(page.doMethod(matches("get"), eq(pageOb), eq("/thing"), isA(Map.class)))
                .andReturn(REDIRECTED_GET);


//        widget.render(pageOb, respond);
//        expectLastCall().once();


        replay(request, page, pageBook, widget, respond, binder);

        Respond out = new WidgetRoutingDispatcher(pageBook, binder, new Provider<Respond>() {
            public Respond get() {
                return respond;
            }
        }, createNiceMock(ResourcesService.class), flashCacheProvider
        ).dispatch(request);


        assert out == respond : "Did not respond correctly";

        verify(request, page, pageBook, widget, respond, binder);

    }

    @Test
    public final void dispatchNothingBecauseOfNoUriMatch() {
        final HttpServletRequest request = createMock(HttpServletRequest.class);
        PageBook pageBook = createMock(PageBook.class);
        RequestBinder binder = createMock(RequestBinder.class);

        @SuppressWarnings("unchecked")
        Provider<Respond> respond = createMock(Provider.class);

        expect(request.getRequestURI())
                .andReturn(A_STATIC_RESOURCE_URI)
                .anyTimes();

        expect(request.getContextPath())
                .andReturn("")
                .anyTimes();

        expect(pageBook.get(A_STATIC_RESOURCE_URI))
                .andReturn(null);

        replay(request, pageBook, respond, binder);

        Respond out = new WidgetRoutingDispatcher(pageBook, binder, respond, createNiceMock(ResourcesService.class),
            flashCacheProvider
        ).dispatch(request);


        assert out == null : "Did not respond correctly";

        verify(request, pageBook, respond, binder);

    }

    @Test
    public final void dispatchStaticResource() {
        final HttpServletRequest request = createMock(HttpServletRequest.class);
        PageBook pageBook = createMock(PageBook.class);
        RequestBinder binder = createMock(RequestBinder.class);
        ResourcesService resourcesService = createMock(ResourcesService.class);
        Respond mockRespond = createMock(Respond.class);

        @SuppressWarnings("unchecked")
        Provider<Respond> respond = createMock(Provider.class);


        expect(request.getRequestURI())
                .andReturn(A_STATIC_RESOURCE_URI)
                .anyTimes();

        expect(request.getContextPath())
                .andReturn("")
                .anyTimes();

        expect(resourcesService.serve(A_STATIC_RESOURCE_URI))
                .andReturn(mockRespond);

        replay(request, pageBook, respond, binder, resourcesService);

        Respond out = new WidgetRoutingDispatcher(pageBook, binder, respond, resourcesService, flashCacheProvider).dispatch(request);


        assert out != null : "Did not respond correctly";
        assert mockRespond.equals(out);

        verify(request, pageBook, respond, binder, resourcesService);

    }
}