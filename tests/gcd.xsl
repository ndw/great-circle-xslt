<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:gcd="http://nwalsh.com/xslt/ext/gcd"
		exclude-result-prefixes="gcd"
                version="2.0">

<xsl:param name="lat1" select="36.12"/>
<xsl:param name="long1" select="-86.67"/>
<xsl:param name="lat2" select="33.94"/>
<xsl:param name="long2" select="-118.40"/>
<xsl:param name="radius" select="6367"/>

<xsl:variable name="distance" select="2884.632203351037"/>
<xsl:variable name="direction" select="85.40642589453718"/>
<xsl:variable name="bearing" select="'E'"/>

<xsl:output method="text"/>

<xsl:template match="/">
  <xsl:variable name="d" select="gcd:distance($lat1,$long1,$lat2,$long2,$radius)"/>
  <xsl:value-of select="$d"/>

  <xsl:choose>
    <xsl:when test="$d = $distance">
      <xsl:value-of select="' PASS&#10;'"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="' FAIL&#10;'"/>
    </xsl:otherwise>
  </xsl:choose>

  <xsl:variable name="d" select="gcd:direction($lat1,$long1,$lat2,$long2)"/>
  <xsl:value-of select="$d"/>

  <xsl:choose>
    <xsl:when test="$d = $direction">
      <xsl:value-of select="' PASS&#10;'"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="' FAIL&#10;'"/>
    </xsl:otherwise>
  </xsl:choose>

  <xsl:variable name="d" select="gcd:bearing($lat1,$long1,$lat2,$long2)"/>
  <xsl:value-of select="$d"/>


  <xsl:choose>
    <xsl:when test="$d = $bearing">
      <xsl:value-of select="' PASS&#10;'"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="' FAIL&#10;'"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
