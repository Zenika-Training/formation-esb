<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:b="http://esb.resanet.com/B"
	xmlns:esb="http://esb.resanet.com/final" exclude-result-prefixes="xsl b">
	<xsl:template match="/">

		<esb:formatFinal>
			<esb:client>
				<xsl:value-of select="/b:formatB/b:id/text()" />
			</esb:client>
		</esb:formatFinal>

	</xsl:template>
</xsl:stylesheet>
