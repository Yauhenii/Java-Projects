<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="text"/>
    <xsl:template match="/">
        <xsl:for-each select="students/student">
            <xsl:text>
            </xsl:text>
            <xsl:value-of select="@id"></xsl:value-of>
            <xsl:value-of select="surname"></xsl:value-of>
            <xsl:value-of select="course"></xsl:value-of>
            <xsl:value-of select="group"></xsl:value-of>
            <xsl:value-of select="@city"></xsl:value-of>
        </xsl:for-each>
        <xsl:variable name="num" select="count(students/student)"/>
        <xsl:variable name="sum" select="sum(students/student/attribute::id) div count(students/student)"/>
        <xsl:value-of select="$num"/>
        <xsl:text>
            Num of students:
            </xsl:text>
        <xsl:value-of select="$sum"/>
        <xsl:text>
            Mean id:
            </xsl:text>
    </xsl:template>
</xsl:stylesheet>