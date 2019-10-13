package org.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;

/**
 * LombokPlugin
 *
 * @description: 加上Lombok注解
 * @author: wujm
 * @create: 2019/7/23
 **/
public class LombokAndSwagerPlugin extends PluginAdapter {
    private FullyQualifiedJavaType data;

    public LombokAndSwagerPlugin() {
        super();
        data = new FullyQualifiedJavaType("lombok.Data");
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {

        topLevelClass.addImportedType(data);
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addJavaDocLine("import org.springframework.format.annotation.DateTimeFormat;");
        topLevelClass.addJavaDocLine("import com.fasterxml.jackson.annotation.JsonFormat;");
        topLevelClass.addJavaDocLine("import io.swagger.annotations.ApiModel;");
        topLevelClass.addJavaDocLine("import io.swagger.annotations.ApiModelProperty;");

        topLevelClass.addAnnotation("@ApiModel(\""+introspectedTable.getFullyQualifiedTable().getRemarks()+"\")");
        return true;
    }



    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {

        field.addAnnotation("@ApiModelProperty(\""+introspectedColumn.getRemarks()+"\")");

        return true;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }
}