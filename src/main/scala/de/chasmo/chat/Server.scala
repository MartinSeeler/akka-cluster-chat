package de.chasmo.chat

import akka.actor.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}

object Server extends App {

  val config = ConfigFactory.load()

  implicit val system = ActorSystem("chat", config.getConfig("client-1").withFallback(config))
//  implicit val system = ActorSystem("chat", config.getConfig("client-2").withFallback(config))

  system.actorOf(User.props("bernd"), "user")
  system.actorOf(UserListener.props, "user-listener")

}
