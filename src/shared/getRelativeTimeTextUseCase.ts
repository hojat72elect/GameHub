export function getRelativeTimeTextUseCase(timestamp: number) {

    if (!timestamp) return "";

    const releaseDate = new Date(timestamp * 1000);
    const now = new Date();
    const diffInMs = releaseDate.getTime() - now.getTime();
    const diffInDays = Math.floor(diffInMs / (1000 * 60 * 60 * 24));
    const absDays = Math.abs(diffInDays);

    if (absDays === 0) return "(today)";

    const years = Math.floor(absDays / 365);
    const months = Math.floor(absDays / 30);
    const days = absDays;

    let timeString = "";
    if (years > 0) {
        timeString = `${years} year${years > 1 ? 's' : ''}`;
    } else if (months > 0) {
        timeString = `${months} month${months > 1 ? 's' : ''}`;
    } else {
        timeString = `${days} day${days > 1 ? 's' : ''}`;
    }

    return diffInDays > 0 ? `(in ${timeString})` : `(${timeString} ago)`;
}