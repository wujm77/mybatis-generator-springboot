package org.mybatis.generator.plugins;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**generate controller class
 *
 * @author wujm
 *
 */
public class MybatisParamPlugin extends PluginAdapter{

	private String paramPack;
	private FullyQualifiedJavaType pojoType;
	private String resultPack;
	private String pojoUrl;
	private FullyQualifiedJavaType RestPageParam;
	private FullyQualifiedJavaType RestParam;
	private FullyQualifiedJavaType lombok;
	private FullyQualifiedJavaType Serializable;
	private String project;
	public MybatisParamPlugin() {
		super();
//		slf4jLogger = new FullyQualifiedJavaType("org.slf4j.Logger");
//		slf4jLoggerFactory = new FullyQualifiedJavaType("org.slf4j.LoggerFactory");
		RestPageParam = new FullyQualifiedJavaType("com.zje.vigilante.common.rest.RestPageParam");
		RestParam = new FullyQualifiedJavaType("com.zje.vigilante.common.rest.RestParam");
		lombok = new FullyQualifiedJavaType("lombok.Data");
		Serializable = new FullyQualifiedJavaType("java.io.Serializable");
	}

	public boolean validate(List<String> warnings) {
		this.paramPack = properties.getProperty("paramPack");
		this.resultPack = properties.getProperty("resultPack");
		this.pojoUrl = context.getJavaModelGeneratorConfiguration().getTargetPackage();
		this.project = properties.getProperty("targetProject");
		return true;
	}


	/**
	 *
	 */
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {

		List<GeneratedJavaFile> files = new ArrayList<GeneratedJavaFile>();

		String table = introspectedTable.getBaseRecordType();//com.rainyn.domain.Account

//		String tableName = table.replaceAll(this.pojoUrl + ".", "").replaceAll("Po", "").trim();//Account

		String tableNameWithPo = table.replaceAll(this.pojoUrl + ".", "").trim();//AccountPo
		String tableName = tableNameWithPo.substring(0, tableNameWithPo.length() );
		FullyQualifiedJavaType get = new FullyQualifiedJavaType(paramPack + "." + tableName + "GetParam");
		pojoType = new FullyQualifiedJavaType(pojoUrl + "." + tableNameWithPo);
		TopLevelClass topLevelClass = new TopLevelClass(get);

		// 导入必须的类
		addImportRestParam(topLevelClass);

		// controller类生成
		addget(topLevelClass,introspectedTable, tableName,files);

		//新增
		FullyQualifiedJavaType save = new FullyQualifiedJavaType(paramPack + "." + tableName + "SaveParam");
		TopLevelClass saveClass = new TopLevelClass(save);
		addImportRestParam( saveClass);
		adSavedget(saveClass,introspectedTable, tableName,files);

		//更新
		FullyQualifiedJavaType update = new FullyQualifiedJavaType(paramPack + "." + tableName + "UpdateParam");
		TopLevelClass updateClass = new TopLevelClass(update);
		addImportRestParam( updateClass);
		addUpdateget(updateClass,introspectedTable, tableName,files);

		//删除
		FullyQualifiedJavaType delete = new FullyQualifiedJavaType(paramPack + "." + tableName + "DeleteParam");
		TopLevelClass deleteClass = new TopLevelClass(delete);
		addImportRestParam( deleteClass);
		addget(deleteClass,introspectedTable, tableName,files);

		//分页
		FullyQualifiedJavaType page = new FullyQualifiedJavaType(paramPack + "." + tableName + "PageParam");
		TopLevelClass pageClass = new TopLevelClass(page);
		addImportRestPageParam( pageClass);
		addPage(pageClass,introspectedTable, tableName,files);


		// 日志类
//		addLogger(topLevelClass);

		return files;
	}


	protected void addget(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String tableName, List<GeneratedJavaFile> files) {

		topLevelClass.addSuperInterface(Serializable);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		topLevelClass.setSuperClass(RestParam);
		topLevelClass.addAnnotation("@Data");

		GeneratedJavaFile file = new GeneratedJavaFile(topLevelClass, project, context.getJavaFormatter());
		Field field = new Field();
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(new FullyQualifiedJavaType("Integer"));
		field.setName("id");
		field.setRemarks("主键");

		topLevelClass.addField(field);
		files.add(file);
	}

	protected void addPage(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String tableName, List<GeneratedJavaFile> files) {

		topLevelClass.addSuperInterface(Serializable);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		topLevelClass.setSuperClass(RestPageParam);
		topLevelClass.addAnnotation("@Data");
//		topLevelClass.addJavaDocLine("import org.springframework.format.annotation.DateTimeFormat;");
//		topLevelClass.addJavaDocLine("import com.fasterxml.jackson.annotation.JsonFormat;");
//		topLevelClass.addJavaDocLine("import io.swagger.annotations.ApiModel;");
//		topLevelClass.addJavaDocLine("import io.swagger.annotations.ApiModelProperty;");
		Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns()
				.iterator();
		while (iter.hasNext()) {
			IntrospectedColumn introspectedColumn = iter.next();
			FullyQualifiedJavaType fqjt = introspectedColumn
					.getFullyQualifiedJavaType();
			String property = introspectedColumn.getJavaProperty();

			Field field = new Field();
//			field.addAnnotation("@ApiModelProperty(\""+introspectedColumn.getRemarks()+"\")");
			field.setVisibility(JavaVisibility.PRIVATE);
			field.setType(fqjt);
			field.setName(property);
			field.setRemarks(introspectedColumn.getRemarks());
			topLevelClass.addField(field);
			topLevelClass.addImportedType(field.getType());
		}

		GeneratedJavaFile file = new GeneratedJavaFile(topLevelClass, project, context.getJavaFormatter());

		files.add(file);
	}

	/**
	 * import must class
	 */
	private void addImportRestPageParam( TopLevelClass topLevelClass) {

		topLevelClass.addImportedType(RestPageParam);
		topLevelClass.addImportedType(lombok);
		topLevelClass.addImportedType(Serializable);
	}

	private void addImportRestParam( TopLevelClass topLevelClass) {

		topLevelClass.addImportedType(RestParam);
		topLevelClass.addImportedType(lombok);
		topLevelClass.addImportedType(Serializable);



	}


	private void addUpdateget(TopLevelClass updateClass, IntrospectedTable introspectedTable, String tableName, List<GeneratedJavaFile> files) {
		updateClass.addImportedType(RestParam);
		updateClass.addImportedType(lombok);
		updateClass.addImportedType(Serializable);
		updateClass.addSuperInterface(Serializable);
		updateClass.setVisibility(JavaVisibility.PUBLIC);
		updateClass.setSuperClass(RestParam);
		updateClass.addAnnotation("@Data");
//		updateClass.addJavaDocLine("import org.springframework.format.annotation.DateTimeFormat;");
//		updateClass.addJavaDocLine("import com.fasterxml.jackson.annotation.JsonFormat;");
//		updateClass.addJavaDocLine("import io.swagger.annotations.ApiModel;");
//		updateClass.addJavaDocLine("import io.swagger.annotations.ApiModelProperty;");
		Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns()
				.iterator();
		while (iter.hasNext()) {
			IntrospectedColumn introspectedColumn = iter.next();
			FullyQualifiedJavaType fqjt = introspectedColumn
					.getFullyQualifiedJavaType();
			String property = introspectedColumn.getJavaProperty();

			Field field = new Field();
//			field.addAnnotation("@ApiModelProperty(\""+introspectedColumn.getRemarks()+"\")");
			field.setVisibility(JavaVisibility.PRIVATE);
			field.setType(fqjt);
			field.setName(property);
			field.setRemarks(introspectedColumn.getRemarks());
			updateClass.addField(field);
			updateClass.addImportedType(field.getType());
		}

		GeneratedJavaFile file = new GeneratedJavaFile(updateClass, project, context.getJavaFormatter());

		files.add(file);

	}

	private void adSavedget(TopLevelClass saveClass, IntrospectedTable introspectedTable, String tableName, List<GeneratedJavaFile> files) {
		saveClass.addImportedType(RestParam);
		saveClass.addImportedType(lombok);
		saveClass.addImportedType(Serializable);
		saveClass.addSuperInterface(Serializable);
		saveClass.setVisibility(JavaVisibility.PUBLIC);
		saveClass.setSuperClass(RestParam);
		saveClass.addAnnotation("@Data");
//		saveClass.addJavaDocLine("import org.springframework.format.annotation.DateTimeFormat;");
//		saveClass.addJavaDocLine("import com.fasterxml.jackson.annotation.JsonFormat;");
//		saveClass.addJavaDocLine("import io.swagger.annotations.ApiModel;");
//		saveClass.addJavaDocLine("import io.swagger.annotations.ApiModelProperty;");
		Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns()
				.iterator();
		while (iter.hasNext()) {
			IntrospectedColumn introspectedColumn = iter.next();
			FullyQualifiedJavaType fqjt = introspectedColumn
					.getFullyQualifiedJavaType();
			String property = introspectedColumn.getJavaProperty();

			Field field = new Field();
//			field.addAnnotation("@ApiModelProperty(\""+introspectedColumn.getRemarks()+"\")");
			field.setVisibility(JavaVisibility.PRIVATE);
			field.setType(fqjt);
			field.setName(property);
			field.setRemarks(introspectedColumn.getRemarks());
			saveClass.addField(field);
			saveClass.addImportedType(field.getType());
		}

		GeneratedJavaFile file = new GeneratedJavaFile(saveClass, project, context.getJavaFormatter());

		files.add(file);


	}


}
