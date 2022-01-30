package com.atguigu.myssm.myspringmvc;

import com.atguigu.myssm.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//import javax.lang.model.element.Element;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.xml.bind.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
//import java.io.InputStream;

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {
    private Map<String, Object> beanMap = new HashMap<>();


    public DispatcherServlet() throws ParserConfigurationException, IOException, SAXException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        // 0. Input the xml file
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
        // 1.创建Factory
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        // 2.创建Builder对象
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//        3. Document obj
        Document document = documentBuilder.parse(inputStream);
        // 4. get all nodes
        NodeList beanNodeList = document.getElementsByTagName("bean");
        for (int i = 0; i < beanNodeList.getLength(); i++) {
            Node beanNode = beanNodeList.item(i);
            if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                Element beanElement = (Element) beanNode;
                String beanId = beanElement.getAttribute("id");
                String className = beanElement.getAttribute("class");
                Class controllerBeanClass = Class.forName(className);
                Object beanObj = controllerBeanClass.newInstance();
                Field servletContextField = controllerBeanClass.getDeclaredField("servletContext");
                servletContextField.setAccessible(true);
                servletContextField.set(beanObj, this.getServletContext());


                beanMap.put(beanId, beanObj);
            }
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.service(req, resp);
        req.setCharacterEncoding("UTF-8");
        String servletPath = req.getServletPath();
//        System.out.println("servletPath= " + servletPath);
        servletPath = servletPath.substring(1);
        int lastDotIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0, lastDotIndex);

        Object controllerBeanObj = beanMap.get(servletPath);

        String operate = req.getParameter("operate");
        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        try {
            Method method = controllerBeanObj.getClass().getDeclaredMethod(operate, HttpServletRequest.class, HttpServletResponse.class);
            if (method != null) {
                method.setAccessible(true);
                method.invoke(controllerBeanObj, req, resp);

            } else {
                throw new RuntimeException("invalid operate value");
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        // 获取当前类中的所有方法
        Method[] Methods = controllerBeanObj.getClass().getDeclaredMethods();
        for (Method m : Methods) {
            String methodName = m.getName();
            if (operate.equals(methodName)) {
                try {
                    m.invoke(this, req, resp);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new RuntimeException("Wrong operation!");
//        System.out.println(servletPath);

    }
}
