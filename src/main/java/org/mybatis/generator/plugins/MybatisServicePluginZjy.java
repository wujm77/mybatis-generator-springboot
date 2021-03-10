package org.mybatis.generator.plugins;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.ArrayList;
import java.util.List;

/**generate service class
 * @author yf-yuanjingkun
 */
public class MybatisServicePluginZjy extends PluginAdapter{

	private FullyQualifiedJavaType PageHelper;
	private FullyQualifiedJavaType Page;
	private FullyQualifiedJavaType ISelect;
//	private FullyQualifiedJavaType slf4jLogger;
//	private FullyQualifiedJavaType slf4jLoggerFactory;
	private FullyQualifiedJavaType serviceType;
	private FullyQualifiedJavaType daoType;
	private FullyQualifiedJavaType interfaceType;
	private FullyQualifiedJavaType pojoType;
	private FullyQualifiedJavaType pojoCriteriaType;
	private FullyQualifiedJavaType listType;
	private FullyQualifiedJavaType RequiredArgsConstructor;
	private FullyQualifiedJavaType service;
	private FullyQualifiedJavaType returnType;
	private FullyQualifiedJavaType Date;
	private FullyQualifiedJavaType Log4j2;
	private String servicePack;
	private String serviceImplPack;
	private String project;
	private String pojoUrl;
	private String tableRemark;//数据库表名注释
	private String dataBaseTableName;//数据库表名

	private FullyQualifiedJavaType RestResult;
	private FullyQualifiedJavaType PageVO;

	/**
	 * 所有的方法
	 */
	private List<Method> methods;
	/**
	 * 是否添加注解
	 */
	private boolean enableAnnotation = true;
	private boolean enableInsert = true;
	private boolean enableInsertSelective = false;
	private boolean enableDeleteByPrimaryKey = false;
	private boolean enableDeleteByExample = false;
	private boolean enableUpdateByExample = false;
	private boolean enableUpdateByExampleSelective = false;
	private boolean enableUpdateByPrimaryKey = false;
	private boolean enableUpdateByPrimaryKeySelective = false;
	private boolean enableList = true;
	private boolean enableSave = true;
	private boolean enableDelete = true;
	private boolean enableUpdate = true;
	private boolean enableGet = true;
	private boolean enablePage = true;
	public MybatisServicePluginZjy() {
//		slf4jLogger = new FullyQualifiedJavaType("org.slf4j.Logger");
//		slf4jLoggerFactory = new FullyQualifiedJavaType("org.slf4j.LoggerFactory");
        RestResult = new FullyQualifiedJavaType("com.zje.vigilante.common.rest.RestResult");
        PageVO = new FullyQualifiedJavaType("com.zje.vigilante.common.rest.PageVO");
		ISelect = new FullyQualifiedJavaType("com.github.pagehelper.ISelect");
		Date = new FullyQualifiedJavaType("java.util.Date");
		Log4j2 = new FullyQualifiedJavaType("lombok.extern.log4j.Log4j2");
		methods = new ArrayList<Method>();

		PageHelper = new FullyQualifiedJavaType("com.github.pagehelper.PageHelper");
		Page =  new FullyQualifiedJavaType("com.github.pagehelper.Page");
	}

	/**
	 * 读取配置文件
	 */
	public boolean validate(List<String> warnings) {
		
		String enableAnnotation = properties.getProperty("enableAnnotation");
		
		String enableSave = properties.getProperty("enableSave");
		
		String enableDelete = properties.getProperty("enableDelete");
		
		String enableUpdate = properties.getProperty("enableUpdate");
		
		String enableGet = properties.getProperty("enableUpdate");
		
//		String enableUpdateByExampleSelective = properties.getProperty("enableUpdateByExampleSelective");

		String enableInsertSelective = properties.getProperty("enableInsertSelective");

		String enableUpdateByPrimaryKey = properties.getProperty("enableUpdateByPrimaryKey");

		String enableDeleteByPrimaryKey = properties.getProperty("enableDeleteByPrimaryKey");

		String enableDeleteByExample = properties.getProperty("enableDeleteByExample");

		String enableUpdateByPrimaryKeySelective = properties.getProperty("enableUpdateByPrimaryKeySelective");

		String enableUpdateByExample = properties.getProperty("enableUpdateByExample");
		
		String enableList = properties.getProperty("enableList");
		
		String enablePage = properties.getProperty("enablePage");
		
		if (StringUtility.stringHasValue(enableAnnotation)){
			this.enableAnnotation = StringUtility.isTrue(enableAnnotation);
		}
		
		if (StringUtility.stringHasValue(enableSave)){
			this.enableSave = StringUtility.isTrue(enableSave);
		}
		
		if (StringUtility.stringHasValue(enableDelete)){
			this.enableDelete = StringUtility.isTrue(enableDelete);
		}
		
		if (StringUtility.stringHasValue(enableUpdate)){
			this.enableUpdate = StringUtility.isTrue(enableUpdate);
		}
		
		if (StringUtility.stringHasValue(enableGet)){
			this.enableGet = StringUtility.isTrue(enableGet);
		}
		
		if (StringUtility.stringHasValue(enableInsertSelective)){
			this.enableInsertSelective = StringUtility.isTrue(enableInsertSelective);
		}
		
		if (StringUtility.stringHasValue(enableUpdateByPrimaryKey)){
			this.enableUpdateByPrimaryKey = StringUtility.isTrue(enableUpdateByPrimaryKey);
		}
		
		if (StringUtility.stringHasValue(enableDeleteByPrimaryKey)){
			this.enableDeleteByPrimaryKey = StringUtility.isTrue(enableDeleteByPrimaryKey);
		}
		
		if (StringUtility.stringHasValue(enableDeleteByExample)){
			this.enableDeleteByExample = StringUtility.isTrue(enableDeleteByExample);
		}
		
		if (StringUtility.stringHasValue(enableUpdateByPrimaryKeySelective)){
			this.enableUpdateByPrimaryKeySelective = StringUtility.isTrue(enableUpdateByPrimaryKeySelective);
		}
		
		if (StringUtility.stringHasValue(enableUpdateByExample)){
			this.enableUpdateByExample = StringUtility.isTrue(enableUpdateByExample);
		}
		
		if (StringUtility.stringHasValue(enableList)){
			this.enableList = StringUtility.isTrue(enableList);
		}
		
		if (StringUtility.stringHasValue(enablePage)){
			this.enablePage = StringUtility.isTrue(enablePage);
		}
		
		this.servicePack = properties.getProperty("targetPackage");
		this.serviceImplPack = properties.getProperty("implementationPackage");
		this.project = properties.getProperty("targetProject"); 
		this.pojoUrl = context.getJavaModelGeneratorConfiguration().getTargetPackage();

		if (this.enableAnnotation) {
			RequiredArgsConstructor = new FullyQualifiedJavaType("lombok.RequiredArgsConstructor");
			service = new FullyQualifiedJavaType("org.springframework.stereotype.Service");
		}
		return true;
	}

	/**
	 * 
	 */
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		List<GeneratedJavaFile> files = new ArrayList<GeneratedJavaFile>();
		String table = introspectedTable.getBaseRecordType();//com.rainyn.domain.Account
		
		String tableNameWithPo = table.replaceAll(this.pojoUrl + ".", "").trim();//AccountPo
		
//		String tableName = table.replaceAll(this.pojoUrl + ".", "").replaceAll("Po", "").trim();//Account
		
		String tableName = tableNameWithPo.substring(0, tableNameWithPo.length() );//Account
		
		interfaceType = new FullyQualifiedJavaType(servicePack + "." + tableName + "Service");//com.rainyn.service.AccountService

		daoType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());//com.rainyn.mapper.AccountMapper

		serviceType = new FullyQualifiedJavaType(serviceImplPack + "." + tableName + "ServiceImpl");//com.rainyn.service.impl.AccountServiceImpl
		
		pojoType = new FullyQualifiedJavaType(pojoUrl + "." + tableNameWithPo);//com.rainyn.service.impl.AccountServiceImpl

		pojoCriteriaType = new FullyQualifiedJavaType(pojoUrl + "."  + tableNameWithPo + "Criteria");//com.rainyn.domain.AccountCriteria

		listType = new FullyQualifiedJavaType("java.util.List");
		
		Interface interface1 = new Interface(interfaceType);
		
		TopLevelClass topLevelClass = new TopLevelClass(serviceType);
		
		// 导入必须的类
		addImport(interface1, topLevelClass);

		// 接口
		addService(interface1,introspectedTable, tableName, files);
		
		// 实现类
		addServiceImpl(topLevelClass,introspectedTable, tableName, files);
		
		// 日志类
//		addLogger(topLevelClass);

		return files;
	}

	/**
	 * add interface
	 * 
	 * @param tableName
	 * @param files
	 */
	protected void addService(Interface interface1,IntrospectedTable introspectedTable, String tableName, List<GeneratedJavaFile> files) {
		
	    tableRemark = introspectedTable.getFullyQualifiedTable().getRemarks().replaceAll("表","");
	    
		dataBaseTableName = introspectedTable.getFullyQualifiedTable().getIntrospectedTableName();
		
		interface1.setVisibility(JavaVisibility.PUBLIC);
		
		Method	method = selectByPrimaryKey(introspectedTable, tableName,false);
		method.removeAllBodyLines();
		addMethodComment(method,"根据主键获取"+(tableRemark.length() == 0 ? pojoType.getShortName():tableRemark));
		interface1.addMethod(method);
		
		method = getOtherInsertboolean("save"+tableName, introspectedTable, tableName,false);
		method.removeAllBodyLines();
		addMethodComment(method,"插入"+(tableRemark.length() == 0 ? pojoType.getShortName():tableRemark));
		interface1.addMethod(method);
	
		method = getOtherInteger("remove"+tableName, introspectedTable, tableName, 2,false);
		method.removeAllBodyLines();
		addMethodComment(method,"根据主键删除"+(tableRemark.length() == 0 ? pojoType.getShortName():tableRemark));
		interface1.addMethod(method);
		
		method = getOtherInteger("update"+tableName, introspectedTable, tableName, 1,false);
		method.removeAllBodyLines();
		addMethodComment(method,"修改"+(tableRemark.length() == 0 ? pojoType.getShortName():tableRemark));
		interface1.addMethod(method);
	
//	    method = selectList(introspectedTable, tableName,false);
//		method.removeAllBodyLines();
//		addMethodComment(method,"列表获取"+(tableRemark.length() == 0 ? pojoType.getShortName():tableRemark));
//		interface1.addMethod(method);
	
	    method = selectPage(introspectedTable, tableName,false);
		method.removeAllBodyLines();
		addMethodComment(method,"分页获取"+(tableRemark.length() == 0 ? pojoType.getShortName():tableRemark));
		interface1.addMethod(method);
		
		GeneratedJavaFile file = new GeneratedJavaFile(interface1, project,context.getJavaFormatter());
        
		files.add(file);
	}

	/**
	 * add implements class
	 * 
	 * @param introspectedTable
	 * @param tableName
	 * @param files
	 */
	protected void addServiceImpl(TopLevelClass topLevelClass,IntrospectedTable introspectedTable, String tableName, List<GeneratedJavaFile> files) {
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		// set implements interface
		topLevelClass.addSuperInterface(interfaceType);

		topLevelClass.addAnnotation("@Service");
        topLevelClass.addAnnotation("@RequiredArgsConstructor");
		topLevelClass.addAnnotation("@Log4j2");
		topLevelClass.addImportedType(service);
		// add import dao
		addField(topLevelClass, tableName);
		// add method
		topLevelClass.addMethod(selectByPrimaryKey(introspectedTable, tableName,true));

		/**
		 * type:  pojo 1 ;key 2 ;example 3 ;pojo+example 4
		 */
		
		topLevelClass.addMethod(getOtherInsertboolean("save"+tableName, introspectedTable, tableName,true));
		
		topLevelClass.addMethod(getOtherInteger("remove"+tableName, introspectedTable, tableName, 2,true));
	
		topLevelClass.addMethod(getOtherInteger("update"+tableName, introspectedTable, tableName, 1,true));
	
//	    topLevelClass.addMethod(selectList(introspectedTable, tableName,true));
	
	    topLevelClass.addMethod(selectPage(introspectedTable, tableName,true));
		
		GeneratedJavaFile file = new GeneratedJavaFile(topLevelClass, project, context.getJavaFormatter());
		files.add(file);
	}

	/**
	 * 添加字段
	 * 
	 * @param topLevelClass
	 */
	protected void addField(TopLevelClass topLevelClass, String tableName) {
		// add dao
		Field field = new Field();
		field.setName(toLowerCase(daoType.getShortName())); // set var name
		topLevelClass.addImportedType(daoType);
		field.setType(daoType); // type
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setFinal(true);
//		if (enableAnnotation) {
//			field.addAnnotation("@Autowired");
//		}
		topLevelClass.addField(field);
	}

	/**
	 * 根据主键获取
	 */
	protected Method selectByPrimaryKey(IntrospectedTable introspectedTable, String tableName, boolean enableAnnotation) {
		Method method = new Method();
		method.setName("get"+tableName);
		method.setReturnType(new FullyQualifiedJavaType("RestResult<"+pojoType+">"));

//		if (introspectedTable.getRules().generatePrimaryKeyClass()) {
//			FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
//			method.addParameter(new Parameter(type, "key"));
//		} else {
//			for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
//				FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
//				method.addParameter(new Parameter(type, introspectedColumn.getJavaProperty()));
//			}
//		}

		method.addParameter(new Parameter(new FullyQualifiedJavaType(pojoType+"GetParam"), "param"));

		method.setVisibility(JavaVisibility.PUBLIC);
		StringBuilder sb = new StringBuilder();
		sb.append(pojoType.getShortName()+" " +toLowerCase(pojoType.getShortName())+" =");
		sb.append(getDaoShort());
		sb.append("selectByPrimaryKey");
		sb.append("(");
//		for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
			sb.append("param.getId()");
//			sb.append(",");
//		}

		sb.append(");");
        sb.append("\r\n\t\t");
		sb.append("if(null == "+toLowerCase(pojoType.getShortName())+"){");
        sb.append("\r\n\t\t");
		sb.append("  return RestResult.wrapErrorResponse(\"数据不存在\");");
        sb.append("\r\n\t\t");
		sb.append("}");
        sb.append("\r\n\t\t");
		sb.append("return RestResult.wrapSuccessResponse("+toLowerCase(pojoType.getShortName())+");");

		method.addBodyLine(sb.toString());
		if(enableAnnotation){
			method.addAnnotation("@Override");
		}
		return method;
	}

//	protected Method selectList(IntrospectedTable introspectedTable, String tableName, boolean enableAnnotation) {
//		Method method = new Method();
//        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
//        FullyQualifiedJavaType listType;
//        listType = new FullyQualifiedJavaType(pojoType.getShortName());
//        returnType.addTypeArgument(listType);
//        method.setReturnType(returnType);
//        method.setName("get"+pojoType.getShortName().substring(0, pojoType.getShortName().length())+"List");
//		method.addParameter(new Parameter(pojoType, toLowerCase(pojoType.getShortName())));
//		method.setVisibility(JavaVisibility.PUBLIC);
//		StringBuilder sb = new StringBuilder();
//		sb.append("List<"+pojoType.getShortName()+">"+" "+"list = ");
//		sb.append(getDaoShort());
//		sb.append("selectAll("+toLowerCase(pojoType.getShortName())+");");
//		sb.append("\r\n\t\t");
//		sb.append("return list;");
//		method.addBodyLine(sb.toString());
//		if(enableAnnotation){
//			method.addAnnotation("@Override");
//		}
//		return method;
//	}
	
	protected Method selectPage(IntrospectedTable introspectedTable, String tableName, boolean enableAnnotation) {
		Method method = new Method();
		FullyQualifiedJavaType  returnType = new FullyQualifiedJavaType("com.github.pagehelper.Page");
        FullyQualifiedJavaType  PageType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        returnType.addTypeArgument(PageType);
		method.setReturnType(new FullyQualifiedJavaType("RestResult<PageVO<"+pojoType.getShortName()+">>"));
        method.setName("get"+pojoType.getShortName().substring(0, pojoType.getShortName().length())+"Page");
		method.addParameter(new Parameter(new FullyQualifiedJavaType(pojoType+"PageParam"), "param"));

		method.setVisibility(JavaVisibility.PUBLIC);
		StringBuilder sb = new StringBuilder();

		sb.append(pojoType.getShortName()+" "+toLowerCase(pojoType.getShortName())+" =");
		sb.append("ObjectCopyUtil.bean2Bean(param,"+pojoType.getShortName()+".class);");
		sb.append("\r\n\t\t");
		sb.append("Page<"+pojoType.getShortName()+">"+" "+"page = ");
		sb.append("PageHelper.startPage(param.getPage(),param.getRows()).doSelectPage(()->");
		sb.append(getDaoShort());
		sb.append("selectAll("+toLowerCase(pojoType.getShortName())+")");
		sb.append(");");
		sb.append("\r\n\t\t");
        sb.append("PageVO<"+pojoType.getShortName()+"> pageVO = new PageVO<>();");
        sb.append("\r\n\t\t");
        sb.append("pageVO.setPageNum(page.getPageNum());");
        sb.append("\r\n\t\t");
        sb.append("pageVO.setPageSize(page.getPageSize());");
        sb.append("\r\n\t\t");
        sb.append("pageVO.setList(page.getResult());");
		sb.append("\r\n\t\t");
		sb.append("pageVO.setTotalSize((int) page.getTotal());");
		sb.append("\r\n\t\t");
		sb.append("pageVO.setTotalPage(page.getPages());");
		sb.append("\r\n\t\t");
		sb.append("return RestResult.wrapSuccessResponse(pageVO);");
		method.addBodyLine(sb.toString());
		if(enableAnnotation){
			method.addAnnotation("@Override");
		}
		return method;
	}
	
	
	
	
	/**
	 * add method
	 * 
	 */
	protected Method countByExample(IntrospectedTable introspectedTable, String tableName) {
		Method method = new Method();
		method.setName("countByExample");
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.addParameter(new Parameter(pojoCriteriaType, "example"));
		method.setVisibility(JavaVisibility.PUBLIC);
		StringBuilder sb = new StringBuilder();
		sb.append("int count = this.");
		sb.append(getDaoShort());
		sb.append("countByExample");
		sb.append("(");
		sb.append("example");
		sb.append(");");
		method.addBodyLine(sb.toString());
		method.addBodyLine("logger.debug(\"count: {}\", count);");
		method.addBodyLine("return count;");
		return method;
	}


	/**
	 * add method
	 * 
	 */
	protected Method getOtherInteger(String methodName, IntrospectedTable introspectedTable, String tableName, int type, boolean enableAnnotation) {
		Method method = new Method();
		method.setName(methodName);
		method.setReturnType(new FullyQualifiedJavaType("RestResult<Integer>"));


		if(methodName.equals("remove"+tableName)){
			method.addParameter(new Parameter(new FullyQualifiedJavaType(pojoType+"DeleteParam"), "param"));
		}else if(methodName.equals("update"+tableName)){
			method.addParameter(new Parameter(new FullyQualifiedJavaType(pojoType+"UpdateParam"), "param"));
		}
//		String params = addParams(introspectedTable, method, type);
		method.setVisibility(JavaVisibility.PUBLIC);
		StringBuilder sb = new StringBuilder();

        if(methodName.equals("update"+tableName)){
            sb.append(pojoType.getShortName()+" "+toLowerCase(pojoType.getShortName()) +" = ObjectCopyUtil.bean2Bean(param,"+pojoType.getShortName()+".class);");
            sb.append("\r\n\t\t");
        }

        sb.append("Integer result = ");

        sb.append(getDaoShort());
//		if (introspectedTable.hasBLOBColumns() && (!"updateByPrimaryKeySelective".equals(methodName) 
//				&& !"deleteByPrimaryKey".equals(methodName)	&& !"deleteByExample".equals(methodName) 
//				&& !"updateByExampleSelective".equals(methodName))) {
//			sb.append(methodName + "WithoutBLOBs");
//		} else {
//			sb.append(methodName);
//		}
		if(methodName.equals("remove"+tableName)){
			sb.append("deleteByPrimaryKey");
		}else if(methodName.equals("update"+tableName)){
			sb.append("updateByPrimaryKey");
		}
		sb.append("(");

		if(methodName.equals("remove"+tableName)){
			sb.append("param.getId()");
		}else if(methodName.equals(toLowerCase("update"+tableName))){
			sb.append(toLowerCase(pojoType.getShortName()));
		}

		sb.append(");");
        sb.append("\r\n\t\t");
        sb.append("return result > 0 ? RestResult.wrapSuccessResponse() : RestResult.wrapErrorResponse(\"操作失败\");");
		method.addBodyLine(sb.toString());
		if(enableAnnotation){
			method.addAnnotation("@Override");
		}
		return method;
	}

	/**
	 * add method
	 * 
	 */
	protected Method getOtherInsertboolean(String methodName, IntrospectedTable introspectedTable, String tableName, boolean enableAnnotation) {
		Method method = new Method();
		method.setName(methodName);
		method.setReturnType(new FullyQualifiedJavaType("RestResult<Integer>"));

		method.addParameter(new Parameter(new FullyQualifiedJavaType(pojoType+"SaveParam"), "param"));

		method.setVisibility(JavaVisibility.PUBLIC);
		StringBuilder sb = new StringBuilder();

        sb.append(pojoType.getShortName()+" "+toLowerCase(pojoType.getShortName()) +" = ObjectCopyUtil.bean2Bean(param,"+pojoType.getShortName()+".class);");
        sb.append("\r\n\t\t");
        sb.append("Integer result = ");
		sb.append(getDaoShort());
		sb.append("insert");
		sb.append("(");
		sb.append(toLowerCase(pojoType.getShortName()));
		sb.append(");");

        sb.append("\r\n\t\t");
        sb.append("return result > 0 ? RestResult.wrapSuccessResponse() : RestResult.wrapErrorResponse(\"操作失败\");");
        method.addBodyLine(sb.toString());
		if(enableAnnotation){
			method.addAnnotation("@Override");
		}
		return method;
	}

	/**
	 * type: pojo 1 key 2 example 3 pojo+example 4
	 */
	protected String addParams(IntrospectedTable introspectedTable, Method method, int type1) {
		switch (type1) {
		case 1:
			method.addParameter(new Parameter(pojoType, toLowerCase(pojoType.getShortName())));
			return toLowerCase(pojoType.getShortName());
		case 2:
			if (introspectedTable.getRules().generatePrimaryKeyClass()) {
				FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
				method.addParameter(new Parameter(type, "key"));
			} else {
				for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
					FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
					method.addParameter(new Parameter(type, introspectedColumn.getJavaProperty()));
				}
			}
			StringBuffer sb = new StringBuffer();
			for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
				sb.append(introspectedColumn.getJavaProperty());
				sb.append(",");
			}
			sb.setLength(sb.length() - 1);
			return sb.toString();
		case 3:
			method.addParameter(new Parameter(pojoCriteriaType, "example"));
			return "example";
		case 4:
			method.addParameter(0, new Parameter(pojoType, toLowerCase(pojoType.getShortName())));
			method.addParameter(1, new Parameter(pojoCriteriaType, "example"));
			return "record, example";
		default:
			break;
		}
		return null;
	}

	protected void addComment(JavaElement field, String comment) {
		StringBuilder sb = new StringBuilder();
		field.addJavaDocLine("/**");
		sb.append(" * ");
		comment = comment.replaceAll("\n", "<br>\n\t * ");
		sb.append(comment);
		field.addJavaDocLine(sb.toString());
		field.addJavaDocLine(" */");
	}

	/**
	 * add field
	 * 
	 * @param topLevelClass
	 */
	protected void addField(TopLevelClass topLevelClass) {
		// add success
		Field field = new Field();
		field.setName("success"); 
		field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance()); // type
		field.setVisibility(JavaVisibility.PRIVATE);
		addComment(field, "excute result");
		topLevelClass.addField(field);
		// set result
		field = new Field();
		field.setName("message"); // set result
		field.setType(FullyQualifiedJavaType.getStringInstance()); // type
		field.setVisibility(JavaVisibility.PRIVATE);
		addComment(field, "message result");
		topLevelClass.addField(field);
	}

	/**
	 * add method
	 * 
	 */
	protected void addMethod(TopLevelClass topLevelClass) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("setSuccess");
		method.addParameter(new Parameter(FullyQualifiedJavaType.getBooleanPrimitiveInstance(), "success"));
		method.addBodyLine("this.success = success;");
		topLevelClass.addMethod(method);

		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
		method.setName("isSuccess");
		method.addBodyLine("return success;");
		topLevelClass.addMethod(method);

		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("setMessage");
		method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "message"));
		method.addBodyLine("this.message = message;");
		topLevelClass.addMethod(method);

		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getStringInstance());
		method.setName("getMessage");
		method.addBodyLine("return message;");
		topLevelClass.addMethod(method);
	}

	/**
	 * add method
	 * 
	 */
	protected void addMethod(TopLevelClass topLevelClass, String tableName) {
		Method method2 = new Method();
		for (int i = 0; i < methods.size(); i++) {
			Method method = new Method();
			method2 = methods.get(i);
			method = method2;
			method.removeAllBodyLines();
			StringBuilder sb = new StringBuilder();
			sb.append("return this.");
			sb.append(getDaoShort());
			sb.append(method.getName());
			sb.append("(");
			List<Parameter> list = method.getParameters();
			for (int j = 0; j < list.size(); j++) {
				sb.append(list.get(j).getName());
				sb.append(",");
			}
			sb.setLength(sb.length() - 1);
			sb.append(");");
			method.addBodyLine(sb.toString());
			topLevelClass.addMethod(method);
		}
		methods.clear();
	}

	/**
	 * BaseUsers to baseUsers
	 * @param tableName
	 * @return
	 */
	protected String toLowerCase(String tableName) {
		StringBuilder sb = new StringBuilder(tableName);
		sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
		return sb.toString();
	}

	/**
	 * BaseUsers to baseUsers
	 * 
	 * @param tableName
	 * @return
	 */
	protected String toUpperCase(String tableName){
		StringBuilder sb = new StringBuilder(tableName);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		return sb.toString();
	}

	/**
	 * import must class
	 */
	private void addImport(Interface interfaces, TopLevelClass topLevelClass) {
		interfaces.addImportedType(pojoType);
		interfaces.addImportedType(PageVO);
//		interfaces.addImportedType(listType);
        interfaces.addImportedType(pojoType);
        interfaces.addImportedType(PageVO);
//        interfaces.addImportedType(listType);
        interfaces.addImportedType(RestResult);
		String pack = servicePack.substring(servicePack.lastIndexOf(".")+1);

		interfaces.addImportedType(new FullyQualifiedJavaType("com.zjy.help.mall.pojo.param."+pack+"."+pojoType.getShortName()+"GetParam"));
		interfaces.addImportedType(new FullyQualifiedJavaType("com.zjy.help.mall.pojo.param."+pack+"."+pojoType.getShortName()+"DeleteParam"));
		interfaces.addImportedType(new FullyQualifiedJavaType("com.zjy.help.mall.pojo.param."+pack+"."+pojoType.getShortName()+"UpdateParam"));
		interfaces.addImportedType(new FullyQualifiedJavaType("com.zjy.help.mall.pojo.param."+pack+"."+pojoType.getShortName()+"PageParam"));
		interfaces.addImportedType(new FullyQualifiedJavaType("com.zjy.help.mall.pojo.param."+pack+"."+pojoType.getShortName()+"SaveParam"));

//		topLevelClass.addImportedType(listType);
		topLevelClass.addImportedType(daoType);
		topLevelClass.addImportedType(interfaceType);
		topLevelClass.addImportedType(pojoType);
//		topLevelClass.addImportedType(slf4jLogger);
//		topLevelClass.addImportedType(slf4jLoggerFactory);
		topLevelClass.addImportedType(RestResult);
//		topLevelClass.addImportedType(ISelect);
//		topLevelClass.addImportedType(Date);
		topLevelClass.addImportedType(Log4j2);
		topLevelClass.addImportedType(RestResult);
		topLevelClass.addImportedType(Page);
		topLevelClass.addImportedType(PageHelper);
//		topLevelClass.addImportedType(ISelect);
        topLevelClass.addImportedType(PageVO);
		topLevelClass.addImportedType("com.zjy.help.mall.pojo.param."+pack+"."+pojoType.getShortName()+"GetParam");
		topLevelClass.addImportedType("com.zjy.help.mall.pojo.param."+pack+"."+pojoType.getShortName()+"DeleteParam");
		topLevelClass.addImportedType("com.zjy.help.mall.pojo.param."+pack+"."+pojoType.getShortName()+"UpdateParam");
		topLevelClass.addImportedType("com.zjy.help.mall.pojo.param."+pack+"."+pojoType.getShortName()+"PageParam");
		topLevelClass.addImportedType("com.zjy.help.mall.pojo.param."+pack+"."+pojoType.getShortName()+"SaveParam");
        topLevelClass.addImportedType("com.zjy.utils.ObjectCopyUtil");
		if (enableAnnotation) {
			topLevelClass.addImportedType(service);
			topLevelClass.addImportedType(RequiredArgsConstructor);
		}
	}

	/**
	 * import logger
	 */
	private void addLogger(TopLevelClass topLevelClass) {
		Field field = new Field();
		field.setFinal(true);
		field.setInitializationString("LoggerFactory.getLogger(" + topLevelClass.getType().getShortName() + ".class)"); 
		field.setName("logger"); 
		field.setStatic(true);
		field.setType(new FullyQualifiedJavaType("Logger")); 
		field.setVisibility(JavaVisibility.PRIVATE);
		topLevelClass.addField(field);
	}

	private String getDaoShort() {
		return toLowerCase(daoType.getShortName()) + ".";
	}

	@Override
	public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
		returnType = method.getReturnType();
		return true;
	}
	
    /**
     * 添加注释
     * @param method
     * @param methodComment
     * @return
     */
    private Method addMethodComment(Method method,String methodComment) {
		
        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * "+methodComment);
        sb.append("\r\n\t");
        List<Parameter> parametersList = method.getParameters();
        for(Parameter parm : parametersList){
        	sb.append(" * @param ");
        	sb.append(parm.getName());
        	sb.append("\r\n\t");
        }
        sb.append(" * @return");
        sb.append(" "+method.getReturnType().getShortName());
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */");
        return method;
    }
	
}
