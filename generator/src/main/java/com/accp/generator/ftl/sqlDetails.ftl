${Domain}表名详情
${tableNameCn}表名详情
<#list fieldList as field>
    java表数据字段: ${field.nameHump};  表注释: ${field.comment};  java属性: ${field.javaType};
    sql表字段名: ${field.name};         sql属性: ${field.type};
    ----------------------------------------------------------
</#list>
