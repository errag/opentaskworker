package com.errag.models;

import androidx.annotation.NonNull;

public enum State
{
    UNKNOWN;

    public enum AirplainMode {
        ON,
        OFF;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum AP {
        ON,
        OFF;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum BatteryLevel {
        CHANGED;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum Battery {
        NOT_CHARGING,
        CHARGING;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum Bluetooth {
        UNKNOWN,
        ON,
        OFF,
        TOGGLE;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum Boot {
        UP;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum Brightness {
        LEVEL;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum Camera {
        SHOT;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum Connection {
        NOT_CONNECTED,
        MOBILE_DATA,
        WIFI;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum DarkMode {
        ON,
        OFF;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum Delay {
        MS;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum Flash {
        ON,
        OFF;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum GPS {
        ON,
        OFF;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum Interruption {
        NOT_RESOLVEABLE,
        ALARMS,
        NONE,
        ALL,
        PRIORITY,
        UNKNOWN;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum Orientation {
        LANDSCAPE,
        PORTRAIT;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum OutgoingCall {
        ON;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum Phone {
        UNKNOWN,
        CALL_STATE_IDLE,
        CALL_STATE_OFFHOOK,
        CALL_STATE_RINGING;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum PowerSaveMove {
        ON,
        OFF;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum ScreenOff {
        OFF;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum ScreenOn {
        ON;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum SMS {
        RECEIVED,
        SEND_TO,
        MESSAGE;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum USB {
        CONNECTED,
        DISCONNECTED;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum Vibrator {
        MS;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum Wifi {
        ON,
        OFF,
        TOGGLE;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }
}