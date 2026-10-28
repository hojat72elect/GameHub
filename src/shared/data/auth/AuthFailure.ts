/**
 * Every possible failure of the authentication flow.
 */
export type AuthFailureReason =
    | "invalidEmail"
    | "missingPassword"
    | "wrongCredentials"
    | "emailAlreadyInUse"
    | "weakPassword"
    | "userDisabled"
    | "providerDisabled"
    | "tooManyRequests"
    | "network"
    | "playServicesUnavailable"
    | "configuration"
    | "cancelled"
    | "unknown";

export class AuthFailure extends Error {
    readonly reason: AuthFailureReason;

    constructor(reason: AuthFailureReason, message?: string) {
        super(message ?? reason);
        this.name = "AuthFailure";
        this.reason = reason;
    }
}
