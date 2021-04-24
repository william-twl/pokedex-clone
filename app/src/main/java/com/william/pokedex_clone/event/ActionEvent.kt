package com.william.pokedex_clone.event

import android.content.Intent
import com.william.pokedex_clone.enum.EventBusAction
import java.io.Serializable

class ActionEvent : Serializable{

    var action = EventBusAction.DEFAULT
    var intent: Intent? = null

    constructor(action: EventBusAction) {
        this.action = action
    }

    constructor(action: EventBusAction, intent: Intent) {
        this.action = action
        this.intent = intent
    }
}
