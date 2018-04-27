package tech.zg.webatis.service;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import tech.zg.webatis.bean.ColumnBean;
import tech.zg.webatis.bean.TableBean;
import tech.zg.webatis.common.DateUtils;
import tech.zg.webatis.exception.BaseExceptionCode;
import tech.zg.webatis.exception.BaseRunTimeException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
public class GenService {

    public static List<String> getTemplates(){
        List<String> templates = new ArrayList<String>();
        templates.add("templates/velocity/Entity.java.vm");
        templates.add("templates/velocity/Mapper.java.vm");
        templates.add("templates/velocity/Mapper.xml.vm");
        templates.add("templates/velocity/Service.java.vm");
        templates.add("templates/velocity/ServiceImpl.java.vm");
        return templates;
    }


    public static List<String> getBaseTemplates() {

        List<String> templates = new ArrayList<String>();
        templates.add("templates/velocity/BaseEntity.java.vm");
        templates.add("templates/velocity/BaseMapper.java.vm");
        templates.add("templates/velocity/BaseService.java.vm");
        templates.add("templates/velocity/BaseServiceImpl.java.vm");
        return templates;
    }

    /**
     * 生成代码
     */
    public static void generatorCode(TableBean tableEntity, List<ColumnBean> columnEntityList, String packagePath, ZipOutputStream zip) {
        //配置信息
        Configuration config = getConfig();
        boolean hasBigDecimal = false;
        //表信息
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix" ));
        //类名，例如sys_user --> SysUser;
        tableEntity.setClassName(className);
        //类名，例如sys_user --> sysUser;
        tableEntity.setClassname(StringUtils.uncapitalize(className));
        //列信息
        for(ColumnBean columnEntity : columnEntityList){
            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            //属性名称(第一个字母大写)，如：user_name => UserName
            columnEntity.setAttrName(attrName);
            //属性名称(第一个字母小写)，如：user_name => userName
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));
            //列的数据类型，转换成Java类型，如果查找不到就是unknowType
            String attrType = config.getString(columnEntity.getDataType(), "unknowType" );
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && attrType.equals("BigDecimal" )) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(columnEntity.getColumnKey()) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }
        }
        tableEntity.setColumnEntityList(columnEntityList);
        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumnEntityList().get(0));
        }
        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );
        Velocity.init(prop);
        packagePath = StringUtils.isBlank(packagePath) ? config.getString("package") : packagePath;
        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("tableComment", tableEntity.getTableComment());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().toLowerCase());        map.put("columns", tableEntity.getColumnEntityList());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("package", packagePath);
        map.put("moduleName", config.getString("moduleName" ));
        map.put("author", config.getString("author" ));
        map.put("email", config.getString("email" ));
        map.put("datetime", DateUtils.formatDate(new Date(), DateUtils.DATETIME_FORMAT));
        map.put("pkType", tableEntity.getPk().getAttrType());

        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8" );
            tpl.merge(context, sw);

            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, tableEntity.getClassName(), packagePath)));
                IOUtils.write(sw.toString(), zip, "UTF-8" );
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new BaseRunTimeException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "" );
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "" );
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("gen.properties" );
        } catch (ConfigurationException e) {
            throw new BaseRunTimeException(BaseExceptionCode.PARSE_CONFIG_FAIL, e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, String packageName) {

        String packagePath = "main" + File.separator + "java" + File.separator;

        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }
        String templateName = template.split("/")[2];
        if (templateName.equals("Entity.java.vm" )) {
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if (templateName.equals("Mapper.java.vm" )) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        }

        if (templateName.equals("Service.java.vm" )) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (templateName.equals("ServiceImpl.java.vm" )) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (templateName.equals("Mapper.xml.vm" )) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + className + "Mapper.xml";
        }

        if (templateName.equals("BaseEntity.java.vm" )) {
            return packagePath + "entity" + File.separator + "BaseEntity.java";
        }

        if (templateName.equals("BaseMapper.java.vm" )) {
            return packagePath + "mapper" + File.separator + "BaseMapper.java";
        }

        if (templateName.equals("BaseService.java.vm" )) {
            return packagePath + "service" + File.separator + "BaseService.java";
        }

        if (templateName.equals("BaseServiceImpl.java.vm" )) {
            return packagePath + "service" + File.separator + "impl" + File.separator + "BaseServiceImpl.java";
        }

        return null;
    }

    public static void generatorBaseCode(String packagePath, ZipOutputStream zip) {

        Configuration config = getConfig();
        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );
        Velocity.init(prop);
        packagePath = StringUtils.isBlank(packagePath) ? config.getString("package") : packagePath;
        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("package", packagePath);
        map.put("moduleName", config.getString("moduleName" ));
        map.put("author", config.getString("author" ));
        map.put("email", config.getString("email" ));
        map.put("datetime", DateUtils.formatDate(new Date(), DateUtils.DATETIME_FORMAT));

        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getBaseTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8" );
            tpl.merge(context, sw);

            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, null, packagePath)));
                IOUtils.write(sw.toString(), zip, "UTF-8" );
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new BaseRunTimeException("渲染基础模板失败，表名：" + e);
            }
        }
    }

}
