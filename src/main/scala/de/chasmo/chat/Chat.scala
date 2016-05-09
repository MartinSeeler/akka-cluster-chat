package de.chasmo.chat

import akka.actor._
import akka.cluster.pubsub.DistributedPubSubMediator.{Publish, Subscribe}
import akka.cluster.pubsub.{DistributedPubSub, DistributedPubSubMediator}
import de.chasmo.chat.Chat.{BroadcastMessage, Message}

class Chat extends Actor with ActorLogging {

  val mediator = DistributedPubSub(context.system).mediator
  mediator ! Subscribe("chat", self)

  def receive: Receive = {
    case BroadcastMessage(from, message) => mediator ! Publish("chat", Message(from, message))
    case Message(from, message) => println(s"$from: $message")
  }

}

object Chat {

  case class Message(from: String, message: String)
  case class BroadcastMessage(from: String, message: String)

  def props: Props = Props(classOf[Chat])

}
