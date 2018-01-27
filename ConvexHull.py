def merge(a,b):
    c = []
    while len(a) != 0 and len(b) != 0:
        if a[0][0] < b[0][0]:
            c.append(a[0])
            a.remove(a[0])
        else:
            c.append(b[0])
            b.remove(b[0])
    if len(a) == 0:
        c += b
    else:
        c += a
    return c

def mergesort(points):

    if len(points) == 0 or len(points) == 1:
        return points
    else:
        middle = len(points)/2
        a = mergesort(points[:middle])
        b = mergesort(points[middle:])
        return merge(a,b)

def getConvexHull(points):



def mergeTwoFinger():


def yInterceptDivider():

	
def main():

	'''given a set of points
	    1. parse the points 
	    2. once you have a collection of points
	    3. sort them by the x-axis
	    4. maintain a Doubly linked list of points representing line segments that are part of the convex hull
	    5. divide the collection into two recurively until the base case of 2 points. The line segment formed by the two points is the convex hull for that set of 2 points
	    6. merge the convex hulls using the two finger algorithm

	'''

	points = [(),(),(),(),()]
	mergesort(points)






main()