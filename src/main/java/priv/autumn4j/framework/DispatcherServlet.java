package priv.autumn4j.framework;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import priv.autumn4j.framework.bean.RequestHandler;
import priv.autumn4j.framework.bean.RequestParam;
import priv.autumn4j.framework.bean.ResponseData;
import priv.autumn4j.framework.bean.ResponseView;
import priv.autumn4j.framework.helper.BeanHelper;
import priv.autumn4j.framework.helper.ConfigHelper;
import priv.autumn4j.framework.helper.ControllerHelper;
import priv.autumn4j.framework.util.CodecUtil;
import priv.autumn4j.framework.util.JsonUtil;
import priv.autumn4j.framework.util.ReflectionUtil;
import priv.autumn4j.framework.util.StreamUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        HelperLoader.init();
        ServletContext servletContext = config.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestPath = req.getPathInfo();
        String requestMethod = req.getMethod();

        RequestHandler requestHandler = ControllerHelper.getRequestHandler(requestPath, requestMethod);
        if (requestHandler != null) {
            Class<?> controllerClazz = requestHandler.getControllerClazz();
            Object controllerInstance = BeanHelper.getBean(controllerClazz);

            //请求参数
            Map<String, Object> paramMap = new HashMap<String, Object>();
            Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramKey = paramNames.nextElement();
                String paramValue = req.getParameter(paramKey);
                paramMap.put(paramKey, paramValue);
            }
            //请求body
            String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));

            if (StringUtils.isNotEmpty(body)) {
                String[] params = StringUtils.split(body, "&");
                if (ArrayUtils.isNotEmpty(params)) {
                    for (String param : params) {
                        String[] kvPair = StringUtils.split(param, "=");
                        if (ArrayUtils.isNotEmpty(kvPair) && ArrayUtils.getLength(kvPair) == 2) {
                            String paramKey = kvPair[0];
                            String paramValue = kvPair[1];
                            paramMap.put(paramKey, paramValue);
                        }
                    }
                }
            }
            //调用action方法
            Method actionMethod = requestHandler.getActionMethod();
            Parameter[] actionParams = actionMethod.getParameters();
            ArrayList<Object> actionParaValues = new ArrayList<>();
            if (ArrayUtils.isNotEmpty(actionParams)) {
                for (Parameter parameter : actionParams) {
                    String paramKey = parameter.getName();
                    if (paramMap.containsKey(paramKey)) {
                        actionParaValues.add(paramMap.get(paramKey));
                    }
                }
            }
            Object result = ReflectionUtil.invokeMethod(controllerInstance, actionMethod, actionParaValues.toArray());
            if (result instanceof ResponseView) {
                ResponseView responseView = (ResponseView) result;
                String path = responseView.getPath();
                if (StringUtils.isNotEmpty(path)) {
                    if (StringUtils.startsWith(path, "/")) {
                        resp.sendRedirect(req.getContextPath() + path);
                    } else {
                        Map<String, Object> model = responseView.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()) {
                            req.setAttribute(entry.getKey(), entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
                    }
                }
            } else if (result instanceof ResponseData) {
                ResponseData responseData = (ResponseData) result;
                Object model = responseData.getModel();
                if (model != null) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    String responseDataJson = JsonUtil.toJson(model);
                    writer.write(responseDataJson);
                    writer.flush();
                    writer.close();
                }
            }
        }
    }


}
