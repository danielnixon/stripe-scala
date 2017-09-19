package org.mdedetrich.stripe.v1

import io.circe._
import cats.syntax.either._
import io.circe.Decoder.Result
import org.mdedetrich.stripe.v1.Subscriptions.Subscription

abstract class StripeObject

final case class UnknownStripeObject(json: Json) extends StripeObject

object UnknownStripeObject {
  implicit val decodeUnknownStripeEvent: Decoder[UnknownStripeObject] = new Decoder[UnknownStripeObject] {
    override def apply(c: HCursor): Result[UnknownStripeObject] = {
      Right(UnknownStripeObject(c.value))
    }
  }
}

object StripeObject {
  implicit val stripeObjectDecoder: Decoder[StripeObject] = Decoder.instance[StripeObject] { c =>
    c.downField("object").as[String].flatMap {
      case "customer"          => implicitly[Decoder[Customers.Customer]].apply(c)
      case "card"              => implicitly[Decoder[Cards.Card]].apply(c)
      case "transfer"          => implicitly[Decoder[Transfers.Transfer]].apply(c)
      case "balance"           => implicitly[Decoder[Balances.Balance]].apply(c)
      case "charge"            => implicitly[Decoder[Charges.Charge]].apply(c)
      case "application_fee"   => implicitly[Decoder[ApplicationFees.ApplicationFee]].apply(c)
      case "account"           => implicitly[Decoder[Accounts.Account]].apply(c)
      case "bank_account"      => implicitly[Decoder[BankAccounts.BankAccount]].apply(c)
      case "coupon"            => implicitly[Decoder[Coupons.Coupon]].apply(c)
      case "discount"          => implicitly[Decoder[Discounts.Discount]].apply(c)
      case "dispute"           => implicitly[Decoder[Disputes.Dispute]].apply(c)
      case "bitcoin_receiver"  => implicitly[Decoder[BitcoinReceivers.BitcoinReceiver]].apply(c)
      case "transfer_reversal" => implicitly[Decoder[TransferReversals.TransferReversal]].apply(c)
      case "subscription"      => implicitly[Decoder[Subscription]].apply(c)
      case _                   => implicitly[Decoder[UnknownStripeObject]].apply(c)
    }
  }

  implicit val stripeObjectEncoder: Encoder[StripeObject] = Encoder.instance[StripeObject] {
    case obj: Customers.Customer                 => implicitly[Encoder[Customers.Customer]].apply(obj)
    case obj: Transfers.Transfer                 => implicitly[Encoder[Transfers.Transfer]].apply(obj)
    case obj: Balances.Balance                   => implicitly[Encoder[Balances.Balance]].apply(obj)
    case obj: Charges.Charge                     => implicitly[Encoder[Charges.Charge]].apply(obj)
    case obj: ApplicationFees.ApplicationFee     => implicitly[Encoder[ApplicationFees.ApplicationFee]].apply(obj)
    case obj: Accounts.Account                   => implicitly[Encoder[Accounts.Account]].apply(obj)
    case obj: BankAccounts.BankAccount           => implicitly[Encoder[BankAccounts.BankAccount]].apply(obj)
    case obj: Coupons.Coupon                     => implicitly[Encoder[Coupons.Coupon]].apply(obj)
    case obj: Discounts.Discount                 => implicitly[Encoder[Discounts.Discount]].apply(obj)
    case obj: Disputes.Dispute                   => implicitly[Encoder[Disputes.Dispute]].apply(obj)
    case obj: Cards.Card                         => implicitly[Encoder[Cards.Card]].apply(obj)
    case obj: BitcoinReceivers.BitcoinReceiver   => implicitly[Encoder[BitcoinReceivers.BitcoinReceiver]].apply(obj)
    case obj: TransferReversals.TransferReversal => implicitly[Encoder[TransferReversals.TransferReversal]].apply(obj)
    case obj: Subscription                       => implicitly[Encoder[Subscription]].apply(obj)
    case obj                                     => throw new IllegalArgumentException(s"Unable to encode object of type ${obj.getClass.getName}")
  }
}
