package org.mdedetrich.stripe.v1

import io.circe.{Decoder, Encoder}

final case class Request(id: String, idempotencyKey: Option[String])

object Request {
  implicit val requestDecoder: Decoder[Request] = Decoder.forProduct2(
    "id",
    "idempotency_key"
  )(Request.apply)

  implicit val requestEncoder: Encoder[Request] = Encoder.forProduct2(
    "id",
    "idempotency_key"
  )(x => (x.id, x.idempotencyKey))
}
