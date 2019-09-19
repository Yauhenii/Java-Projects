<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html"/>
    <xsl:template match="/">
        <html>
            <body>
                <h2>Students</h2>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th>ID</th>
                        <th>Surname</th>
                        <th>Course</th>
                        <th>Group</th>
                        <th>City</th>
                    </tr>

                    <xsl:for-each select="students/student">
                        <tr>
                            <td><xsl:value-of select="@id"></xsl:value-of></td>
                            <td><xsl:value-of select="surname"></xsl:value-of></td>
                            <td><xsl:value-of select="course"></xsl:value-of></td>
                            <td><xsl:value-of select="group"></xsl:value-of></td>
                            <td><xsl:value-of select="@city"></xsl:value-of></td>
                        </tr>
                    </xsl:for-each>
                </table>
                <xsl:variable name="num" select="count(students/student)"/>
                <xsl:variable name="sum" select="sum(students/student/attribute::id) div count(students/student)"/>
                <p>Num of students:<xsl:value-of select="$num"/></p>
                <p>Mean ID:<xsl:value-of select="$sum"/></p>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>