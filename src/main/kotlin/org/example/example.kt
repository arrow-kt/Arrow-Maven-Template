@file:Suppress("RedundantSuspendModifier")
package org.example

import arrow.core.Either
import arrow.core.computations.either

object Lettuce
object Knife
object Salad

sealed class CookingException {
    object LettuceIsRotten : CookingException()
    object KnifeNeedsSharpening : CookingException()
    data class InsufficientAmount(val quantityInGrams: Int) : CookingException()
}

typealias NastyLettuce = CookingException.LettuceIsRotten
typealias KnifeIsDull = CookingException.KnifeNeedsSharpening
typealias InsufficientAmountOfLettuce = CookingException.InsufficientAmount

fun takeFoodFromRefrigerator(): Either<NastyLettuce, Lettuce> =
    Either.Right(Lettuce)

suspend fun getKnife(): Either<KnifeIsDull, Knife> =
    Either.Right(Knife)

fun prepare(tool: Knife, ingredient: Lettuce): Either<InsufficientAmountOfLettuce, Salad> =
    Either.Left(InsufficientAmountOfLettuce(5))

suspend fun prepareLunch(): Either<CookingException, Salad> =
    either {
        val lettuce = takeFoodFromRefrigerator().bind()
        val knife = getKnife().bind()
        val lunch = prepare(knife, lettuce).bind()
        lunch
    }