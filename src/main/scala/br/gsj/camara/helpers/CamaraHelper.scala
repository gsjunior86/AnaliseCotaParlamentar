package br.gsj.camara.helpers

import java.sql.Timestamp

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

  case class DespesaCotaParlamentar(
    txNomeParlamentar: String,
    cpf: Option[String],
    ideCadastro: Option[String],
    nuCarteiraParlamentar: Option[Int],
    nuLegislatura: Int,
    sgUF: String,
    sgPartido: Option[String],
    codLegislatura: Int,
    numSubCota: Int,
    txDescricao: String,
    numEspecificacaoSubCota: Int,
    txtDescricaoEspecificacao: Option[String],
    txtFornecedor: String,
    txtCNPJCPF: String,
    txtNumero: Long,
    indTipoDocumento: Int,
    datEmissao: Timestamp,
    vlrDocumento: Double,
    vlrGlosa: Double,
    vlrLiquido: Double,
    numMes: Int,
    numAno: Int,
    numParcela: Int,
    txtPassageiro: Option[String],
    txtTrecho: Option[String],
    numLote: Long,
    nuDeputadoId: Int,
    urlDocumento: String
  )
}
