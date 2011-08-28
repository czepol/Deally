package pl.deally {
package lib {

object HtmlHelpers {
  def slugify(in: String) = {
    in.trim.toLowerCase.
      replaceAll(" ", "-").
      replaceAll("[àáâãäåāăąǎǻ]", "a").
      replaceAll("[çćĉċč]", "c").
      replaceAll("[ďđ]", "d").
      replaceAll("[èéêëēĕėęě]", "e").
      replaceAll("[ƒ]", "f").
      replaceAll("[ĝğġģ]", "g").
      replaceAll("[ĥħ]", "h").
      replaceAll("[ìíîïĩīĭįıǐ]", "i").
      replaceAll("[ĵ]", "j").
      replaceAll("[ķ]", "k").
      replaceAll("[ĺļľŀł]", "l").
      replaceAll("[ńņňŉñ]", "n").
      replaceAll("[òóôõöøōŏőơǒǿ]", "o").
      replaceAll("[ŕŗř]", "r").
      replaceAll("[śŝşšſ]", "s").
      replaceAll("[ţťŧ]", "t").
      replaceAll("[ùúûüũūŭůűųưǔǖǘǚǜ]", "u").
      replaceAll("[ŵ]", "w").
      replaceAll("[ýÿŷ]", "y").
      replaceAll("[źżž]", "z").
      replaceAll("[æǽ]", "ae").
      replaceAll("[œ]", "oe").
      replaceAll("[ĳ]", "ij").
      replaceAll("[^a-z0-9-]+", "").
      replaceAll("-+", "-").
	    replaceAll("(^\\-*|\\-*$)", "")
  }
  
  def trimAfter(in: String, len: Int, sep: String): String = {
    if(in.length <= len)
      in
    else
      in.substring(0, len)+sep
  }
  
  def trimAfter(in: String, len: Int): String = {
    trimAfter(in, len, "") 
  }
}

}
}
