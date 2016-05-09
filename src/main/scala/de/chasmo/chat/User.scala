package de.chasmo.chat

import akka.actor._

import scala.concurrent.duration._
import scala.io.StdIn


class User(name: String) extends Actor with ActorLogging {

  import context.dispatcher
  context.system.scheduler.scheduleOnce(0 seconds, getNextUserMessage)

  private[this] val chat = context.actorOf(Chat.props, "chat")

  def receive: Receive = {
    case _ =>
  }

  private def getNextUserMessage: Runnable = new Runnable {
    def run(): Unit = {
      val message = StdIn.readLine()
      chat ! Chat.BroadcastMessage(name, message)
      context.system.scheduler.scheduleOnce(0 seconds, getNextUserMessage)
    }
  }

}

object User {

  def props(name: String): Props = Props(classOf[User], name)

}
