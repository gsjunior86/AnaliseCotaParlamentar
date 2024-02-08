package br.gsj.camara.helpers

object CamaraHelper {

  case class Deputado(
    uri: String,
    nome: String,
    nomeCivil: String,
    idLegislaturaInicial: Int,
    idLegislaturaFinal: Int,
    siglaSexo: String,
    urlRedeSocial: Option[String]
  )

}
